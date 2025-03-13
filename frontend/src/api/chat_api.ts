import axios from "axios";
import { CommonResponseDto } from "../types/member_types";
import { ChatbotRequestDto, ChatbotResponseDto } from "../types/chatbot_types";

// Axios 인스턴스 생성
const api = axios.create({
  // baseURL: import.meta.env.VITE_LLM_HOST, // 백엔드 주소
    baseURL: 'http://localhost:8182', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': import.meta.env.CONTENT_TYPE,
    },
});


export const chatHealth = async (): Promise<string> => {
  const response = await api.get<string>(`/llm/health`);
  return response.data; 
};

// 날씨 검색 요청
export const fetchWeather = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  const response = await api.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/weather/info`, data);
  return response.data;
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


