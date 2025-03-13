import axios from "axios";
import { CommonResponseDto, InfoResponseDto } from "../types/member_types";

// Axios 인스턴스 생성
const api = axios.create({
    // baseURL: import.meta.env.VITE_AUTH_HOST, // 백엔드 주소
    baseURL: 'http://localhost:8180', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': import.meta.env.CONTENT_TYPE,
    },
});


// 인증 갱신
export const renewAccess = async (): Promise<CommonResponseDto<InfoResponseDto>> => {
  const response = await api.post<CommonResponseDto<InfoResponseDto>>('/member/renew');   
  return response.data;
};


// 로그아웃
export const logoutMember = async (): Promise<CommonResponseDto<null>> => {
    const response = await api.post<CommonResponseDto<null>>(`/auth/member/logout`);
    return response.data;
};