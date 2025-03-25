from typing import TypeVar, Generic, Optional
from pydantic import BaseModel  # ✅ 최신 pydantic 적용

T = TypeVar("T")


class CommonResponseDto(BaseModel, Generic[T]):
    status: str  # ✅ 필수 필드 (누락 시 오류 발생)
    message: str
    data: Optional[T] = None

