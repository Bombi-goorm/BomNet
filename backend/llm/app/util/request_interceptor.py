import time
import logging
from starlette.middleware.base import BaseHTTPMiddleware
from starlette.requests import Request
from starlette.responses import Response

logger = logging.getLogger("alert_logger")

class RequestTimerMiddleware(BaseHTTPMiddleware):
    async def dispatch(self, request: Request, call_next):
        start_time = time.time()

        # 요청 처리
        response: Response = await call_next(request)

        process_time = (time.time() - start_time) * 1000  # ms 단위
        formatted_time = f"{process_time:.2f}ms"

        logger.info(
            f"[요청 처리 시간] {request.method} {request.url.path} → {formatted_time}"
        )

        return response