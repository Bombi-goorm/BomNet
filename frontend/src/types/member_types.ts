import { Product } from "./product_types";

// 공통 응답 타입
export interface CommonResponseDto<T> {
    status: string, // 응답 코드 
    message: string, // 응답 메세지
    data: T, // DTO 객체
}

// 등록 요청
export interface SignupRequestDto {
    PNU?: string,
}


// 사용자 정보 응답 
export interface InfoResponseDto {
    memberId: string,
    email: string,
    PNU?: string,
    myFarm: MyFarmInfo,
    recommendedProducts: RecommendedProducts[],
}

// 농장 정보
export interface MyFarmInfo {
    sido: string, // 시/도 명
    destrict: string, // 시/군/구 명
    sidoCode: string, // 시/도 코드
    destrictCode: string, // 시/군/구 코드
    soilType: string, // 토양 타입
    averageTemperture: string, // 평균 기온
    annualprecipitation: string, // 연간 강수량
}

// 내 농장 추천 상품
export interface RecommendedProducts {
    productName: string,
    reason: string,
}

// 관심품목
export interface interestItems {
    id: number,
    products: Product, 
    inWatch: string, // 추적여부
}

// 알림목록
export interface NotificationList {
    id: number,
    type: string, // 기상/가격 
    content: string, // 알림 내용
}
