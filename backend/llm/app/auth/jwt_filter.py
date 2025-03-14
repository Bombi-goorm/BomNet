import base64
import hashlib
import hmac
import os
import requests
import jwt
from jwt import ExpiredSignatureError, InvalidTokenError
from fastapi import Request, HTTPException, Depends
from starlette.middleware.base import BaseHTTPMiddleware
from sqlalchemy.orm import Session
from app.database import SessionLocal
from app.model import Member

# ✅ 환경변수 가져오기
AUTH_SERVER_URL = os.getenv("AUTH_SERVER_URL")
JWT_SECRET = os.getenv("JWT_SECRET")
JWT_ALGORITHM = os.getenv("JWT_ALGORITHM")  # 기본 알고리즘 설정

print("✅ AUTH_SERVER_URL:", AUTH_SERVER_URL)
print("✅ JWT_SECRET:", JWT_SECRET)  # 보안상 직접 출력 X
print("✅ JWT_ALGORITHM:", JWT_ALGORITHM)

# 🚨 여기서 추가 해싱하지 말고, 그대로 사용!


print("✅ JWT_SECRET_KEY 생성 완료")

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

        print(access_token)
        print(refresh_token)

        if not access_token:
            raise HTTPException(status_code=401, detail="Missing access token")

        try:
            # ✅ 1. JWT 토큰 검증 및 사용자 ID(member_id) 추출
            payload = jwt.decode(access_token, JWT_SECRET, algorithms=[JWT_ALGORITHM])

            print(payload)

            member_id = payload.get("sub")

            print(member_id)
            print('11111111')

            # ✅ 2. DB에서 사용자 객체 조회
            user = db.query(Member).filter(Member.member_id == member_id).first()
            if not user:
                raise HTTPException(status_code=401, detail="User not found")

            # ✅ 3. 인증된 사용자 객체를 요청에 저장
            request.state.user = user

        except ExpiredSignatureError:
            # ✅ 4. 액세스 토큰 만료 → 리프레시 토큰 확인
            if not refresh_token:
                raise HTTPException(status_code=401, detail="Token expired and no refresh token found")

            new_access_token = await self.refresh_access_token(refresh_token, db)
            request.state.user = self.verify_access_token(new_access_token, db)

            # ✅ 5. 새로운 토큰으로 응답 생성
            response = await call_next(request)
            response.set_cookie(key="access_token", value=new_access_token, httponly=True, secure=True)
            return response

        except InvalidTokenError:
            raise HTTPException(status_code=401, detail="Invalid token")

        # ✅ 6. 요청 계속 진행
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

            user = db.query(Member).filter(Member.member_id == member_id).first()
            if not user:
                raise HTTPException(status_code=401, detail="User not found")

            return user
        except (ExpiredSignatureError, InvalidTokenError):
            raise HTTPException(status_code=401, detail="Invalid token")