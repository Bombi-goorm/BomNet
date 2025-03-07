import json
from fastapi import APIRouter
from openai import OpenAI
from pydantic import BaseModel
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.config import settings

other_router = APIRouter()
client = OpenAI(api_key=settings.OPENAI_API_KEY)


# GPT 응답 모델
class GPTResponse(BaseModel):
    intent: str  # 사용자의 질문 의도
    crop: str  # 작물명 (예: 사용자 입력의 첫 단어)
    response_data: dict  # JSON 응답 데이터


@other_router.post("/request", response_model=CommonResponseDto[GPTResponse])
async def ask_other_question(data: ChatbotRequestDto):
    """🌱 자연어 분석 기반의 GPT API - 다양한 농업 관련 질문 처리"""

    try:
        # ✅ 1. LLM을 호출하여 질문의 intent 분석 (동적인 카테고리 확장 가능)
        intent_detection_response = client.chat.completions.create(
            model="gpt-4-turbo",
            messages=[
                {
                    "role": "system",
                    "content": (
                        "You are an AI that classifies user queries related to agriculture. "
                        "If the question matches one of these predefined intents: "
                        "['disease_pest_info', 'cultivation_method', 'variety_list', 'price_info', 'general_info'], return that intent. "
                        "Otherwise, create a new descriptive intent based on the question. "
                        "Example: If the user asks about '사과 가격', return 'price_info'. "
                        "Just return the intent name as a single word."
                    )
                },
                {"role": "user", "content": f"Classify this query: '{data.input}'"}
            ]
        )

        intent = intent_detection_response.choices[0].message.content.strip().lower()
        print(f"🔹 Detected Intent: {intent}")

        # ✅ 2. 의도별 응답 템플릿 정의 (각 항목 최대 5개 반환)
        response_templates = {
            "disease_pest_info": "<Crop> 병충해 목록입니다\n- 질병1\n- 질병2\n- 질병3\n- 질병4\n- 질병5",
            "cultivation_method": "<Crop> 재배 방법입니다\n- 방법1\n- 방법2\n- 방법3\n- 방법4\n- 방법5",
            "variety_list": "<Crop> 품종 목록입니다\n- 품종1\n- 품종2\n- 품종3\n- 품종4\n- 품종5",
            "price_info": "<Crop> 최근 가격 변동\n- 1000원 (1월 1일)\n- 1200원 (1월 5일)\n- 1500원 (1월 10일)\n- 1600원 (1월 15일)\n- 1800원 (1월 20일)",
            "general_info": "<Crop> 관련 정보입니다\n- 정보1\n- 정보2\n- 정보3\n- 정보4\n- 정보5"
        }

        # ✅ 3. LLM을 호출하여 실제 응답 생성 (의도에 따라 다르게 요청)
        if intent in response_templates:
            response_message = (
                f"Return a structured JSON response with the following format:\n"
                f"{{'content': '{response_templates[intent]}'}}"
            )
        else:
            # ✅ 새로 생성된 intent에 대한 기본 응답 템플릿
            response_message = (
                f"Return structured JSON information about '{intent}'. "
                f"Format the response as follows:\n"
                f"{{'content': '<Crop> {intent} 정보입니다\\n- 항목1\\n- 항목2\\n- 항목3\\n- 항목4\\n- 항목5'}}"
            )

        gpt_api_response = client.chat.completions.create(
            model="gpt-4-turbo",
            messages=[
                {"role": "system", "content": response_message},
                {"role": "user", "content": f"Provide information about '{data.input}'."}
            ],
            functions=[
                {
                    "name": "generate_response",
                    "description": "Generate structured response in JSON format",
                    "parameters": {
                        "type": "object",
                        "properties": {
                            "content": {"type": "string", "description": "Formatted response"}
                        },
                        "required": ["content"]
                    }
                }
            ],
            function_call={"name": "generate_response"}
        )

        # ✅ 4. 응답 JSON 변환
        function_call = gpt_api_response.choices[0].message.function_call
        if function_call:
            response_json = json.loads(function_call.arguments)
        else:
            response_json = {"content": "❌ 응답을 생성하는 데 실패했습니다."}

        return CommonResponseDto(
            status="200",
            message=f"✅ '{data.input}'에 대한 정보를 제공합니다.",
            data=GPTResponse(
                intent=intent,
                crop=data.input.split()[0],
                response_data=response_json
            )
        )

    except Exception as e:
        return CommonResponseDto(
            status="500",
            message=f"⛔ GPT API 처리 중 오류 발생: {str(e)}",
            data=None
        )