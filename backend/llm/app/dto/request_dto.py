from pydantic import BaseModel


class ChatbotRequestDto(BaseModel):
    input: str = None
    bigId: str = None
    midId: str = None
    smallId: str = None
    region: str = None
    price: int = None
