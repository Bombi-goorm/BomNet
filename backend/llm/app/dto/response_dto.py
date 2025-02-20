import datetime

from pydantic import BaseModel


# 응답

class ChatbotResponseDto(BaseModel):
    product_id: int
    price: int
    availability: int



