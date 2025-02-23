from fastapi import APIRouter
from typing import Optional

from fastapi import APIRouter
from openai import OpenAI
from pydantic import BaseModel

from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from config import OPENAI_API_KEY

other_router = APIRouter()

# ✅ OpenAI API 클라이언트
client = OpenAI(api_key=OPENAI_API_KEY)


# ✅ GPT 응답 모델
class GPTResponse(BaseModel):
    intent: str  # 사용자의 질문 의도
    crop: Optional[str]  # 작물명 (필요 시)
    response_data: dict  # OpenAI의 응답 데이터


@other_router.post("/request", response_model=CommonResponseDto[GPTResponse])
async def ask_other_question(data: ChatbotRequestDto):
    """🌱 자연어 분석 기반의 GPT API - 다양한 농업 관련 질문 처리"""

    try:
        # ✅ 1. 사용자 입력을 분석하여 의도(Intent) 추출
        intent_detection_response = client.chat.completions.create(
            model="gpt-4-turbo",
            messages=[
                {"role": "system", "content": "You are an AI that classifies user queries related to agriculture."},
                {"role": "user", "content": f"'{data.input}'의 질문 유형을 다음 중 하나로 분류해줘: "
                                            "['disease_pest_info', 'cultivation_method', 'variety_list', 'general_info']."}
            ]
        )

        # ✅ GPT 응답에서 intent 추출
        intent = intent_detection_response.choices[0].message.content.strip().lower()

        # ✅ 2. Intent에 따라 세부 응답 생성
        if intent == "disease_pest_info":
            # 🌿 병충해 정보 조회
            response_message = "작물의 병충해 정보를 카테고리별로 정리해 주세요."
        elif intent == "cultivation_method":
            # 🌱 재배법 정보 조회
            response_message = "작물의 재배 방법을 단계별로 설명해 주세요."
        elif intent == "variety_list":
            # 🍎 품종 목록 조회
            response_message = "작물의 품종 목록을 정리해 주세요."
        else:
            # 🤖 일반적인 질문 (기타 농업 정보)
            response_message = "사용자의 질문에 대한 자세한 정보를 제공해 주세요."

        # ✅ 3. OpenAI API를 호출하여 실제 응답 생성
        gpt_response = client.chat.completions.create(
            model="gpt-4-turbo",
            messages=[
                {"role": "system", "content": response_message},
                {"role": "user", "content": f"'{data.input}'에 대한 정보를 제공해줘."}
            ]
        )

        # ✅ GPT 응답을 JSON으로 변환
        response_data = gpt_response.choices[0].message.content.strip()

        return CommonResponseDto(
            status="200",
            message=f"✅ '{data.input}'에 대한 정보를 제공합니다.",
            data=GPTResponse(intent=intent, crop=data.input.split()[0], response_data={"content": response_data})
        )

    except Exception as e:
        return CommonResponseDto(
            status="500",
            message=f"⛔ GPT API 처리 중 오류 발생: {str(e)}",
            data=None
        )