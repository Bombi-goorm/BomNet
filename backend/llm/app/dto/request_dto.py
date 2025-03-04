from enum import Enum
from typing import Optional

from pydantic import BaseModel


class NotificationType(str, Enum):
    WEATHER = "특보"
    TARGET_PRICE = "지정가"


class ChatbotRequestDto(BaseModel):
    input: str = None
    bigId: str = None
    midId: str = None
    smallId: str = None
    region: str = None
    price: int = None
