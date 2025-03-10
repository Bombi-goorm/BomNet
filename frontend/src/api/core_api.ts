import axios from "axios";
import { CommonResponseDto, InfoResponseDto, SignupRequestDto } from "../types/member_types";
import { HomeDto, HomeRequestDto } from "../types/home_types";
import { PriceResponse } from "../types/price_types";
import { ProductRequestDto } from "../types/product_types";
import { data, priceResponse } from "../data_sample";

// Axios 인스턴스 생성
const api = axios.create({
    // baseURL: 'https://bomnet.co.kr', // 백엔드 주소
    baseURL: 'http://localhost:8181', // 로컬 테스트
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


// 사용자 정보 요청
export const memberInfo = async (): Promise<CommonResponseDto<InfoResponseDto>> => {
    const response = await api.post<CommonResponseDto<InfoResponseDto>>(`/core/member/info`);
    return response.data;
};


// 홈화면 정보 요청
export const homeInfo = async (data: HomeRequestDto): Promise<CommonResponseDto<HomeDto>> => {
    const response = await api.post<CommonResponseDto<HomeDto>>(`/core/home`, data);
    return response.data;
};

// 홈화면 테스트
// export const homeInfo = (): HomeDto => {
//   return data;
// };

// 품목 가격정보 검색 - 비인증
export const itemPriceSearch = async (data: ProductRequestDto): Promise<CommonResponseDto<PriceResponse>> => {
  // const response = await api.post<CommonResponseDto<PriceResponse>>('/core/item/price', data);   
  // return response.data;
  return {
    status: "200",  // API 응답 형식에 맞춰 success 값 추가
    message: "가격 데이터를 성공적으로 조회했습니다.",
    data: priceResponse, // 로컬에 있는 priceResponse 반환
  };
};


// 인증 갱신
export const renewAccess = async (): Promise<CommonResponseDto<InfoResponseDto>> => {
  const response = await api.post<CommonResponseDto<InfoResponseDto>>('/core/renew');   
  return response.data;
};