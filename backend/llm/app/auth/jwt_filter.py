import base64

import jwt
import requests
from fastapi import Request, HTTPException
from jwt import ExpiredSignatureError, InvalidTokenError
from sqlalchemy.orm import Session
from starlette.middleware.base import BaseHTTPMiddleware

from app.config import settings
from app.database import SessionLocal
from app.model import Member

import logging

logger = logging.getLogger("jwt_logger")

AUTH_SERVER_URL = settings.AUTH_SERVER_URL
JWT_SECRET = settings.JWT_SECRET
JWT_ALGORITHM = settings.JWT_ALGORITHM

print("JWT_SECRET::", JWT_SECRET)
print("JWT_ALGORITHM::",JWT_ALGORITHM)


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


class JwtFilter(BaseHTTPMiddleware):
    """ 모든 요청에서 JWT를 검증하고 사용자 인증을 수행하는 미들웨어 """
    async def dispatch(self, request: Request, call_next):
        # ✅ 헬스 체크 URL 우회
        if request.url.path.startswith("/llm/base/health"):
            logger.info("LLM::Healthy")
            return await call_next(request)

        access_token = request.cookies.get("access_token")
        refresh_token = request.cookies.get("refresh_token")
        db: Session = next(get_db())  # 데이터베이스 세션 생성

        key_bytes = base64.b64decode(JWT_SECRET).decode("utf-8")

        try:
            if not access_token:
                logger.error(":: No Access Token")
                raise ExpiredSignatureError  # 강제로 토큰 만료 처리 → 리프레시 토큰 갱신 흐름으로 이동

            # ✅ 1. 액세스 토큰 검증
            try:
                # ✅ 토큰 디코딩 시도
                payload = jwt.decode(access_token, key_bytes, algorithms=[JWT_ALGORITHM])
                member_id = payload.get("sub")
            except ExpiredSignatureError:
                logger.error(":: Access Token Expired ❗")
                raise
            except InvalidTokenError as e:
                logger.error(f":: Invalid Access Token ❗ Reason → {str(e)}")
                raise

            # ✅ 2. 사용자 조회
            member = db.query(Member).filter(Member.id == member_id).first()
            if not member:
                logger.error(":: Member not found ❗")
                raise HTTPException(status_code=401, detail="Member not found")

            request.state.member = member

        except (ExpiredSignatureError, InvalidTokenError) as e:
            # ✅ access_token이 없거나 만료, 검증 실패 모두 여기로 옴
            if not refresh_token:
                logger.error(f":: No Refresh Token :: {str(e)}")
                raise HTTPException(status_code=401, detail="Missing refresh token")

            # ✅ 리프레시 토큰으로 새 액세스 토큰 요청
            new_access_token = await self.refresh_access_token(refresh_token, db)

            # ✅ 새 토큰 검증 및 사용자 조회
            member = self.verify_access_token(new_access_token, db)
            request.state.member = member

            # ✅ 응답에 새 토큰 설정 imageUrl
            response = await call_next(request)
            response.set_cookie(key="access_token", value=new_access_token, httponly=True, secure=True)
            return response

            # ✅ 정상 검증 후 요청 계속 처리
        response = await call_next(request)
        return response

    async def refresh_access_token(self, refresh_token: str, db: Session) -> str:
        """ 인증 서버에 refresh_token을 보내서 새로운 access_token 발급 """
        if not AUTH_SERVER_URL:
            logger.error(f":: AUTH_SERVER_URL :: {str(AUTH_SERVER_URL)}")
            raise HTTPException(status_code=500, detail="AUTH_SERVER_URL is not set")

        refresh_url = f"{AUTH_SERVER_URL}/member/renew"
        response = requests.post(
            refresh_url,
            cookies={"refresh_token": refresh_token}
        )

        if response.status_code != 200:
            logger.error(f":: Token refresh failed - status={str(response.status_code)}")
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
                logger.error(f":: User not found")
                raise HTTPException(status_code=401, detail="User not found")

            return user
        except (ExpiredSignatureError, InvalidTokenError) as e:
            logger.error(f":: Token decode failed: {str(e)}")
            raise HTTPException(status_code=401, detail="Invalid token")
