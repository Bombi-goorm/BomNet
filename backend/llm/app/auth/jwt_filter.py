import base64
import hashlib
import hmac
import os
import requests
import jwt
from jwt import ExpiredSignatureError, InvalidTokenError
from fastapi import Request, HTTPException, Depends
from sqlalchemy import UUID
from starlette.middleware.base import BaseHTTPMiddleware
from sqlalchemy.orm import Session
from app.database import SessionLocal
from app.model import Member

# ✅ 환경변수 가져오기
AUTH_SERVER_URL = os.getenv("AUTH_SERVER_URL")
JWT_SECRET = os.getenv("JWT_SECRET")
JWT_ALGORITHM = os.getenv("JWT_ALGORITHM")  # 기본 알고리즘 설정

# print("✅ AUTH_SERVER_URL:", AUTH_SERVER_URL)
# print("✅ JWT_SECRET:", JWT_SECRET)  # 보안상 직접 출력 X
# print("✅ JWT_ALGORITHM:", JWT_ALGORITHM)

# 🚨 여기서 추가 해싱하지 말고, 그대로 사용!


# print("✅ JWT_SECRET_KEY 생성 완료")

def get_db():
    """ 데이터베이스 세션 생성 """
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


class JwtFilter(BaseHTTPMiddleware):
    """ 모든 요청에서 JWT를 검증하고 사용자 인증을 수행하는 미들웨어 """

    async def dispatch(self, request: Request, call_next):
        access_token = request.cookies.get("access_token")
        refresh_token = request.cookies.get("refresh_token")
        db: Session = next(get_db())  # 데이터베이스 세션 생성

        JWT_SECRET_KEY = base64.b64decode(JWT_SECRET)

        try:
            if not access_token:
                raise ExpiredSignatureError  # 강제로 토큰 만료 처리 → 리프레시 토큰 갱신 흐름으로 이동

            # ✅ 1. 액세스 토큰 검증
            payload = jwt.decode(access_token, JWT_SECRET_KEY, algorithms=[JWT_ALGORITHM])
            member_id = payload.get("sub")

            # ✅ 2. 사용자 조회
            member = db.query(Member).filter(Member.id == member_id).first()
            if not member:
                raise HTTPException(status_code=401, detail="Member not found")

            request.state.member = member

        except (ExpiredSignatureError, InvalidTokenError):
            # ✅ access_token이 없거나 만료, 검증 실패 모두 여기로 옴
            if not refresh_token:
                raise HTTPException(status_code=401, detail="Missing refresh token")

            # ✅ 리프레시 토큰으로 새 액세스 토큰 요청
            new_access_token = await self.refresh_access_token(refresh_token, db)

            # ✅ 새 토큰 검증 및 사용자 조회
            member = self.verify_access_token(new_access_token, db)
            request.state.member = member

            # ✅ 응답에 새 토큰 설정
            response = await call_next(request)
            response.set_cookie(key="access_token", value=new_access_token, httponly=True, secure=True)
            return response

            # ✅ 정상 검증 후 요청 계속 처리
        response = await call_next(request)
        return response

    async def refresh_access_token(self, refresh_token: str, db: Session) -> str:
        """ 인증 서버에 refresh_token을 보내서 새로운 access_token을 받음 """
        if not AUTH_SERVER_URL:
            raise HTTPException(status_code=500, detail="AUTH_SERVER_URL is not set")

        refresh_url = f"{AUTH_SERVER_URL}/member/renew"
        response = requests.post(refresh_url, json={"refresh_token": refresh_token})

        if response.status_code != 200:
            raise HTTPException(status_code=401, detail="Token refresh failed")

        new_access_token = response.json().get("access_token")
        return new_access_token

    def verify_access_token(self, token: str, db: Session) -> Member:
        """ 새로운 액세스 토큰 검증 및 사용자 조회 """
        try:
            payload = jwt.decode(token, JWT_SECRET, algorithms=[JWT_ALGORITHM])
            member_id = payload.get("sub")

            user = db.query(Member).filter(Member.id == member_id).first()
            if not user:
                raise HTTPException(status_code=401, detail="User not found")

            return user
        except (ExpiredSignatureError, InvalidTokenError):
            raise HTTPException(status_code=401, detail="Invalid token")