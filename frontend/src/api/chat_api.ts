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

export const chatDB = async (): Promise<string> => {
  const response = await api.get<string>(`/llm/ping`);
  return response.data;
};

// 날씨 검색 요청
export const fetchWeather = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  const response = await axios.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/weather`, data);
  return response.data;
};

// 가격 조회 요청
export const fetchPrice = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  console.log('11111')
  const response = await axios.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/price`, data);
  return response.data;
};

// 알람 설정 요청
export const fetchAlert = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  const response = await axios.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/alert`, data);
  return response.data;
};

// 기타 질문 요청 (GPT)
export const fetchOther = async (data: ChatbotRequestDto): Promise<CommonResponseDto<ChatbotResponseDto>> => {
  const response = await axios.post<CommonResponseDto<ChatbotResponseDto>>(`/llm/other`, data);
  return response.data;
};