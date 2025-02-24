import json
from fastapi import APIRouter, HTTPException
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from openai import OpenAI

from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from config import OPENAI_API_KEY

alert_router = APIRouter()

# ✅ 데이터베이스 설정
DATABASE_URL = "mysql+pymysql://root:1234@localhost:3306/bomnet_db"
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# ✅ OpenAI 클라이언트 초기화
client = OpenAI(api_key=OPENAI_API_KEY)


async def extract_alert_info(user_input: str):
    """🌟 LLM을 사용하여 사용자의 자연어 입력에서 품목, 품종, 가격을 추출"""
    try:
        response = client.chat.completions.create(
            model="gpt-4-turbo",
            messages=[
                {"role": "system", "content": "Extract the item, variety, and price from user input in JSON format."},
                {"role": "user", "content": f"'{user_input}'에서 품목, 품종, 가격을 추출해줘."}
            ],
            functions=[
                {
                    "name": "extract_price_alert",
                    "description": "Extract item, variety, and price from user input.",
                    "parameters": {
                        "type": "object",
                        "properties": {
                            "item": {"type": "string", "description": "The name of the main category (e.g., '사과')"},
                            "variety": {"type": "string",
                                        "description": "The specific variety of the item (e.g., '홍옥')"},
                            "price": {"type": "integer", "description": "The price threshold for alerts (e.g., 3000)"}
                        },
                        "required": ["item", "variety", "price"]
                    }
                }
            ],
            function_call={"name": "extract_price_alert"}
        )

        # ✅ 응답을 JSON으로 변환
        function_response = json.loads(response.choices[0].message.function_call.arguments)

        return function_response

    except Exception as e:
        print(f"⛔ GPT API 오류: {e}")
        raise HTTPException(status_code=500, detail=f"GPT API 오류: {str(e)}")


@alert_router.post("/set", response_model=CommonResponseDto[None])
async def set_alert(data: ChatbotRequestDto):
    """✅ LLM을 사용하여 품목, 품종, 가격을 추출한 후 알람 설정"""

    # ✅ 1. LLM을 통해 품목, 품종, 가격 추출
    extracted_data = await extract_alert_info(data.input)

    item = extracted_data.get("item", "알 수 없음")
    variety = extracted_data.get("variety", "알 수 없음")
    price = extracted_data.get("price", "가격 미정")

    # ✅ 2. 최종 응답 메시지
    message = f"🔔 {item} ({variety}) 알람이 {price}원으로 설정되었습니다."

    return CommonResponseDto(success=True, message=message, data=None)