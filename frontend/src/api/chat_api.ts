import axios from "axios";
import { CommonResponseDto } from "../types/member_types";
import { ChatbotRequestDto, ChatbotResponseDto } from "../types/chatbot_types";

// Axios 인스턴스 생성
const api = axios.create({
    // baseURL: 'https://bomnet.co.kr', // 백엔드 주소
    baseURL: 'http://localhost:8182', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': 'application/json',
    },
});


export const chatHealth = async (): Promise<string> => {
  const response = await api.get<string>(`/llm/health`);
  return response.data; 
};

// 날씨 검색 요청
// export const fetchWeather = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
//   const response = await api.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/weather/info`, data);
//   return response.data;
// };

export const fetchWeather = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  return {
    status: "200",
    message: "날씨 정보 조회 성공",
    data: {
      location: "서울",
      weatherInfo: {
        weather: "맑음",
        temperature: "5°C",
        humidity: "30%",
        wind: "10km/h",
        dateTime: "2023-02-01T12:00:00Z",
      },
    },
  };
};


// 가격 조회 요청
export const fetchPrice = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  const response = await api.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/price/info`, data);
  return response.data;
};

// 알람 설정 요청
export const fetchAlert = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  const response = await api.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/alert/set`, data);
  return response.data;
};

// 기타 질문 요청 (GPT)
export const fetchOther = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  const response = await api.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/other/request`, data);
  return response.data;
};


