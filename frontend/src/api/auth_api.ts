import axios from "axios";
import { CommonResponseDto, SignupRequestDto } from "../types/types";

// Axios 인스턴스 생성
const api = axios.create({
    // baseURL: 'https://bomnet.co.kr', // 백엔드 주소
    baseURL: 'http://localhost:8180', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': 'application/json',
    },
});


// 신규등록
export const signup = async (data: SignupRequestDto): Promise<CommonResponseDto<null>> => {
    const response = await api.post<CommonResponseDto<null>>(`/auth/member/signup`, data);
    return response.data;
};



// 로그아웃
export const logoutMember = async (): Promise<CommonResponseDto<null>> => {
    const response = await api.post<CommonResponseDto<null>>(`/auth/member/logout`);
    return response.data;
};