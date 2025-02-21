
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from main import router


@router.post("/other", response_model=CommonResponseDto[OtherResponseDto])
async def ask_other_question(data: ChatbotRequestDto):
    """GPT API로 농업 관련 질문 처리"""
    answer = f"🤖 GPT 응답: '{data.query}'에 대한 답변을 준비 중입니다."
    return CommonResponseDto(success=True, message="✅ 질문에 대한 응답을 제공합니다.", data=OtherResponseDto(answer=answer))