from typing import TypeVar, Generic

from pydantic.generics import GenericModel

T = TypeVar("T")


# 공통 응답
class CommonResponseDto(GenericModel, Generic[T]):
    status: str
    message: str
    data: T
