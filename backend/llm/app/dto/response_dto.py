from pydantic import BaseModel


class ChatbotResponseDto(BaseModel):
    product_id: int
    price: int
    availability: int


class PriceResponseDto(BaseModel):
    crop: str
    price: int


class OtherResponseDto(BaseModel):
    answer: str
