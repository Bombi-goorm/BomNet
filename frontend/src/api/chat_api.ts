import axios from "axios";
import { ChatbotRequestDto, CommonResponseDto } from "../types/member_types";

// Axios 인스턴스 생성
const api = axios.create({
    // baseURL: 'https://bomnet.co.kr', // 백엔드 주소
    baseURL: 'http://localhost:8182', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': 'application/json',
    },
});


// 챗봇 요청
export const chatRequest = async (data: ChatbotRequestDto): Promise<CommonResponseDto<null>> => {
    const response = await api.post<CommonResponseDto<null>>(`/llm/chat`, data);
    return response.data;
};