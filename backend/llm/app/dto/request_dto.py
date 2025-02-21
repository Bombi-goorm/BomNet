from pydantic import BaseModel


class ChatbotRequestDto(BaseModel):
    input: str
    item: str
    mid_id: int
    small_id: int
    price: int



