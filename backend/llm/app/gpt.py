from fastapi import FastAPI
from fastapi import APIRouter
from pydantic import BaseModel
import openai
from fastapi.responses import JSONResponse
import json
from app.config import OPENAI_API_KEY

# Initialize FastAPI
# app = FastAPI()
router = APIRouter()

# OpenAI API Key 설정
openai.api_key = OPENAI_API_KEY

# 품목-품종 매핑
ITEM_VARIETY_MAP = {
    "부사": "Apple", 
    "홍로": "Apple", 
    "샤인머스켓": "Grape", 
    "캠벨": "Grape",
    "청상추": "Lettuce", 
    "적상추": "Lettuce", 
    "대파": "Green Onion"
}

# @app.post("/category")
# async def process_input(request: Request):
#     data = await request.json()
#     user_input = data.get('input')
#     """ 사용자 입력을 분석하고 적절한 기능을 호출 """
#     button_map = {
#         "가격 정보": extract_price_info,
#         "가격 알림 설정": extract_price_info,
#         "날씨 정보": extract_crop_recommendation,
#         "챗봇 문의": lambda x: {"intent": "chatbot_response", "message": "일반적인 문의를 도와드립니다."}
#     }

#     # 버튼 입력일 경우 바로 해당 함수 호출
#     if user_input in button_map:
#         return button_map[user_input](user_input)

#     return button_map

class UserInput(BaseModel):
    user_input: str

@router.post("/alarm")
def extract_price_info(user_input: UserInput):
    """GPT를 활용하여 품목, 품종, 가격 정보를 추출"""
    client = openai.OpenAI(api_key=OPENAI_API_KEY)

    response = client.chat.completions.create(
        model="gpt-4-turbo",
        messages=[
            {"role": "system", "content": "Extract item, variety, and price from user input."},

            {"role": "user", "content": user_input.user_input}

        ],
        functions=[
            {
                "name": "extract_price_alert",
                "description": "Extract item, variety, and price from the user's request.",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "Item": {"type": "string"},
                        "Variety": {"type": "string"},
                        "Price": {"type": "integer"},
                    },
                    "required": ["Variety"]
                }
            }
        ],
        function_call={"name": "extract_price_alert"}
    )

    function_response = json.loads(response.choices[0].message.function_call.arguments)

    
    # JSON 응답 생성
    response_data = {
        "status": "success",
        "data": {
            "Variety": function_response.get("Variety")
        }
    }

    # 선택적 필드 추가
    if function_response.get("Item"):
        response_data["data"]["Item"] = function_response.get("Item")
    if function_response.get("Price"):
        response_data["data"]["Price"] = function_response.get("Price")

    # JSONResponse를 사용하여 JSON 형식으로 반환
    return JSONResponse(content=response_data)


@router.post("/weather")
async def get_seoul_weather():
    try:
        client = openai.OpenAI(api_key=OPENAI_API_KEY)
        response = client.chat.completions.create(

            model="gpt-4-turbo",

            messages=[
                {"role": "system", "content": "You are a weather assistant."},
                {"role": "user", "content": "서울의 현재 날씨를 알려줘."}
            ]
        )
        weather_info = response.choices[0].message.content

        return JSONResponse(content={
            "status": "success",
            "data": {"weather": weather_info}
        })
    except Exception as e:
        return JSONResponse(
            status_code=500,
            content={"status": "error", "message": str(e)}
        )

@router.post("/other")
async def ask_agriculture_question(user_input: UserInput):

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

        return JSONResponse(content={
            "status": "success",
            "data": {"answer": answer}
        })
        
    except Exception as e:
        return JSONResponse(
            status_code=500,
            content={
                "status": "error",
                "message": str(e)
            }
        )


@router.post("/price")
async def extract_varieties(user_input: UserInput):
    try:
        client = openai.OpenAI(api_key=OPENAI_API_KEY)
        
        response = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are an AI that extracts crop variety names from user input."},
                {"role": "user", "content": user_input.user_input}
            ],

            functions=[

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

            function_call={"name": "extract_variety"}
        )


        function_call = response.choices[0].message.function_call
        if function_call:
            function_response = json.loads(function_call.arguments)
            item = function_response.get("Item")
            variety = function_response.get("Variety")

            result = {
                "status": "success",
                "data": {"Variety": variety}
            }
            if item:
                result["data"]["Item"] = item
            
            return JSONResponse(content=result)
        else:
            return JSONResponse(
                status_code=400,
                content={
                    "status": "error",
                    "message": "Failed to extract variety information"
                }
            )

    except Exception as e:
        return JSONResponse(
            status_code=500,
            content={
                "status": "error",
                "message": str(e)
            }
        )


@router.post("/recommend")
def extract_crop_recommendation(user_input: UserInput):
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

@router.post("/pest")
def extract_pest_info(user_input: UserInput):  
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

