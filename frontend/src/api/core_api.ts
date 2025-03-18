import axios from "axios";
import { CommonResponseDto, InfoResponseDto, PriceAlertCondition, SignupRequestDto, UserNotification } from "../types/member_types";
import { HomeDto, HomeRequestDto } from "../types/home_types";
import { PriceResponse } from "../types/price_types";
import { ProductRequestDto, ProductResponseDto } from "../types/product_types";
// import { data, priceResponse, productResponse } from "../data_sample";

// Axios 인스턴스 생성
const api = axios.create({
    baseURL: import.meta.env.VITE_CORE_HOST, // 백엔드 주소
    // baseURL: 'https://core.bomnet.shop', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': 'application/json',
    },
});


// 신규등록
export const signup = async (data: SignupRequestDto): Promise<CommonResponseDto<null>> => {
    const response = await api.post<CommonResponseDto<null>>(`/core/members/pnu`, data);
    return response.data;
};


// 사용자 정보 요청
export const getMemberInfo = async (): Promise<CommonResponseDto<InfoResponseDto>> => {
    const response = await api.get<CommonResponseDto<InfoResponseDto>>(`/core/members`);
    return response.data;
};

// 구독 정보 저장
export const pushSubscribtion = async (data: HomeRequestDto): Promise<CommonResponseDto<InfoResponseDto>> => {
  const response = await api.post<CommonResponseDto<InfoResponseDto>>(`/core/member/push`, data);
  return response.data;
};


// 홈화면 정보 요청
export const getHomeInfo = async (): Promise<CommonResponseDto<HomeDto>> => {
    const response = await api.post<CommonResponseDto<HomeDto>>(`/core/home`);
    return response.data;
};

// 홈화면 테스트
// export const homeInfo = (): HomeDto => {
//   return data;
// };

// 품목 가격정보 검색 - 비인증
export const itemPriceSearch = async (data: ProductRequestDto): Promise<CommonResponseDto<PriceResponse>> => {
  const response = await api.post<CommonResponseDto<PriceResponse>>('/core/item/price', data);  
  console.log(response.data) 
  return response.data;
  // return {
  //   status: "200",  // API 응답 형식에 맞춰 success 값 추가
  //   message: "가격 데이터를 성공적으로 조회했습니다.",
  //   data: priceResponse, // 로컬에 있는 priceResponse 반환
  // };
};

// 상품 + PNU로 재배조건 및 적합도 평가
export const productInfo = async (data: ProductRequestDto): Promise<CommonResponseDto<ProductResponseDto>> => {
  const response = await api.post<CommonResponseDto<ProductResponseDto>>(`/core/item/info`, data);
  return response.data;
  // return {
  //   status: "200",  
  //   message: "상품 데이터를 성공적으로 조회했습니다.",
  //   data: productResponse, // 로컬에 있는 priceResponse 반환
  // };
};



// 알림 목록 조회
export const getNotifications = async (): Promise<CommonResponseDto<UserNotification[]>> => {
  const response = await api.get<CommonResponseDto<UserNotification[]>>(`/core/notifications`);
  return response.data;
};

// 알림 전부 읽음처리
export const readAllNotifications = async (): Promise<CommonResponseDto<string>> => {
  const response = await api.post<CommonResponseDto<string>>(`/core/notifications/all`);
  return response.data;
};

// 알림 읽음처리
export const readNotification = async (data: UserNotification): Promise<CommonResponseDto<string>> => {
  const response = await api.post<CommonResponseDto<string>>(`/core/notifications`, data);
  return response.data;
};


// 알림 조건 삭제
export const removeNotificationCondition = async (data: PriceAlertCondition): Promise<CommonResponseDto<PriceAlertCondition[]>> => {
  const response = await api.post<CommonResponseDto<PriceAlertCondition[]>>(`/notification/condition`, data);
  return response.data;
};