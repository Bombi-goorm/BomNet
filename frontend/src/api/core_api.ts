import axios from "axios";
import { CommonResponseDto, HomeDto, InfoResponseDto } from "../types/member_types";
import { data } from "../data_sample";

// Axios 인스턴스 생성
const api = axios.create({
    // baseURL: 'https://bomnet.co.kr', // 백엔드 주소
    baseURL: 'http://localhost:8181', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': 'application/json',
    },
});

// 사용자 정보 요청
export const memberInfo = async (): Promise<CommonResponseDto<InfoResponseDto>> => {
    const response = await api.post<CommonResponseDto<InfoResponseDto>>(`/core/member/info`);
    return response.data;
};


// 홈화면 정보 요청
// export const homeInfo = async (): Promise<CommonResponseDto<HomeDto>> => {
//     const response = await api.post<CommonResponseDto<HomeDto>>(`/core/home/info`);
//     return response.data;
// };

// 홈화면 테스트
export const homeInfo = (): HomeDto => {
  return data;
};


// 인증 갱신
export const renewAccess = async (): Promise<CommonResponseDto<InfoResponseDto>> => {
  const response = await api.post<CommonResponseDto<InfoResponseDto>>('/core/renew');   
  return response.data;
};