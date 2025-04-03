from enum import Enum

from pydantic import BaseModel


class NotificationType(str, Enum):
    WEATHER = "특보"
    TARGET_PRICE = "지정가"


class ChatbotRequestDto(BaseModel):
    member_id: str = None
    input: str = None
    bigId: str = None
    midId: str = None
    smallId: str = None
    region: str = None
    price: int = None
