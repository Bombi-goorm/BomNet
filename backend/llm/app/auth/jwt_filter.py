import os
import requests
import jwt
from jwt import ExpiredSignatureError, InvalidTokenError

from fastapi import Request, HTTPException
from starlette.middleware.base import BaseHTTPMiddleware

AUTH_SERVER_URL = os.getenv("AUTH_SERVER_URL")
JWT_SECRET = os.getenv("JWT_SECRET")
JWT_ALGORITHM = os.getenv("JWT_ALGORITHM")


class JwtFilter(BaseHTTPMiddleware):
    """ 모든 요청에 대해 JWT를 검증하는 미들웨어 """

    async def dispatch(self, request: Request, call_next):
        access_token = request.cookies.get("access_token")
        refresh_token = request.cookies.get("refresh_token")

        if not access_token:
            raise HTTPException(status_code=401, detail="Missing access token")

        try:
            payload = jwt.decode(access_token, JWT_SECRET, algorithms=[JWT_ALGORITHM])
            request.state.user_email = payload.get("sub")
        except ExpiredSignatureError:
            if not refresh_token:
                raise HTTPException(status_code=401, detail="Token expired and no refresh token found")

            new_access_token = self.refresh_access_token(refresh_token)
            request.state.user_email = self.verify_access_token(new_access_token)

            response = await call_next(request)
            response.set_cookie(key="access_token", value=new_access_token, httponly=True, secure=True)
            return response
        except InvalidTokenError:
            raise HTTPException(status_code=401, detail="Invalid token")

        response = await call_next(request)
        return response

    def refresh_access_token(self, refresh_token: str) -> str:
        if not AUTH_SERVER_URL:
            raise HTTPException(status_code=500, detail="AUTH_SERVER_URL is not set")

        refresh_url = f"{AUTH_SERVER_URL}/auth/refresh"
        response = requests.post(refresh_url, json={"refresh_token": refresh_token})

        if response.status_code != 200:
            raise HTTPException(status_code=401, detail="Token refresh failed")

        return response.json().get("access_token")

    def verify_access_token(self, token: str) -> str:
        try:
            payload = jwt.decode(token, JWT_SECRET, algorithms=[JWT_ALGORITHM])
            return payload.get("sub")
        except (ExpiredSignatureError, InvalidTokenError):
            raise HTTPException(status_code=401, detail="Invalid token")
