from pydantic import BaseModel


# 요청

class ChatbotRequestDto(BaseModel):
    input: str
