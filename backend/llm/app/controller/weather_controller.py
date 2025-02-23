import json

import openai
from fastapi import APIRouter
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from app.dto.request_dto import ChatbotRequestDto
from config import OPENAI_API_KEY

weather_router = APIRouter()


# Database connection setup
DATABASE_URL = "mysql+pymysql://root:1234@localhost:3306/bomnet_db"

# Create SQLAlchemy engine and session
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


# @router.post("/")
# async def get_seoul_weather():
#     """ 서울의 날씨를 OpenAI GPT로 요청 """
#     try:
#         client = openai.OpenAI(api_key=OPENAI_API_KEY)
#         response = client.chat.completions.create(
#             model="gpt-3.5-turbo",
#             messages=[
#                 {"role": "system", "content": "You are an AI weather assistant. Provide structured weather data in JSON format."},
#                 {"role": "user", "content": "현재 서울의 기온, 습도, 날씨 상태, 풍속을 JSON 형식으로 제공해줘."}
#             ],
#             temperature=0.5
#         )
#
#         # 응답 JSON 변환
#         raw_response = response.choices[0].message.content
#         try:
#             weather_info = json.loads(raw_response)
#         except json.JSONDecodeError:
#             raise HTTPException(status_code=500, detail="Invalid JSON response from OpenAI API")
#
#         return {"weather": weather_info}
#
#     except openai.error.OpenAIError as e:
#         raise HTTPException(status_code=500, detail=f"OpenAI API error: {str(e)}")
#     except Exception as e:
#         raise HTTPException(status_code=500, detail=f"Unexpected error: {str(e)}")


@weather_router.post("/other")
async def ask_agriculture_question(user_input: ChatbotRequestDto):
    """
    Asks a question related to agriculture using GPT API and returns the response.

    :param user_input: UserInput model containing the user's agriculture-related question
    :return: JSON response containing the AI's answer
    """
    try:
        client = openai.OpenAI(api_key=OPENAI_API_KEY)

        prompt = f"You are an expert in agriculture. Answer the following question:\n\n{user_input.user_input}\n\nAnswer:"

        response = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are an AI assistant specialized in agriculture."},
                {"role": "user", "content": prompt}
            ]
        )

        answer = response.choices[0].message.content.strip()

        return {
            "status": "success",
            "answer": answer
        }

    except Exception as e:
        return {
            "status": "error",
            "message": str(e)
        }


@weather_router.post("/price")
async def extract_varieties(user_input: ChatbotRequestDto):
    try:
        client = openai.OpenAI(api_key=OPENAI_API_KEY)

        response = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are an AI that extracts crop variety names from user input."},
                {"role": "user", "content": user_input.user_input}
            ],
            functions=[  # 'functions'로 수정 (이전의 'function'에서)
                {
                    "name": "extract_variety",
                    "description": "Extract item and variety from the user's input",
                    "parameters": {
                        "type": "object",
                        "properties": {
                            "Item": {"type": "string"},
                            "Variety": {"type": "string"},
                        },
                        "required": ["Variety"]
                    }
                }
            ],
            function_call={"name": "extract_variety"}  # function_call 파라미터 추가
        )

        # 응답 처리
        function_call = response.choices[0].message.function_call
        if function_call:
            function_response = json.loads(function_call.arguments)
            item = function_response.get("Item")
            variety = function_response.get("Variety")

            result = {"Variety": variety}
            if item:
                result["Item"] = item

            return result
        else:
            return {"error": "Failed to extract variety information"}

    except Exception as e:
        return {"error": str(e)}


@weather_router.post("/recommend")
def extract_crop_recommendation(user_input: ChatbotRequestDto):
    """GPT를 활용하여 지역 기반 작물 추천"""
    client = openai.OpenAI(api_key=OPENAI_API_KEY)

    response = client.chat.completions.create(
        model="gpt-4-turbo",
        messages=[
            {"role": "system", "content": "Recommend suitable crops based on the given location."},
            {"role": "user", "content": user_input}
        ]
    )

    return {"intent": "crop_recommendation", "message": response.choices[0].message.content}


@weather_router.post("/pest")
def extract_pest_info(user_input: ChatbotRequestDto):
    """사용자 입력에서 작물명을 추출하고 GPT를 통해 병해충 정보를 가져옴"""
    crop_name = user_input.user_input.replace("에 잘 생기는 병해충 알려줘", "").strip()
    if not crop_name:
        return {"intent": "pest_info", "message": "어떤 작물의 병해충 정보를 원하시나요?"}

    pests = get_pest_info(crop_name)
    return {"intent": "pest_info", "crop": crop_name, "pests": pests}


def get_pest_info(crop_name):
    """GPT를 사용하여 병해충 정보 제공"""
    client = openai.OpenAI(api_key=OPENAI_API_KEY)
    response = client.chat.completions.create(
        model="gpt-4-turbo",
        messages=[
            {"role": "system", "content": f"{crop_name}에 영향을 미치는 병해충 정보를 제공해 주세요."},
            {"role": "user", "content": f"{crop_name}의 일반적인 병해충은 무엇인가요?"}
        ]
    )
    pests_info = response.choices[0].message.content.split("\n")
    return pests_info
