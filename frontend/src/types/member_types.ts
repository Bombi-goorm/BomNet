// 공통 응답 타입
export interface CommonResponseDto<T> {
    status: string, // 응답 코드 
    message: string, // 응답 메세지
    data: T, // DTO 객체
}

// 등록 요청
export interface SignupRequestDto {
    pnu?: string,
}


// 사용자 정보 응답 
export interface InfoResponseDto {
    memberId: string,
    email: string,
    pnu?: string,
    joinDate: string,
    myFarm: FarmData,
    recommendedProducts: RecommendedProduct[],
    notificationConditions: PriceAlertCondition[],
}

// 내 농장 추천 상품
export interface RecommendedProduct {
    id: number,
    productName: string,
    reason: string,
}


// 알림 조건 목록
export interface PriceAlertCondition {
    id: number,
    item: string,
    variety: string,
    markets: string,
    price: string,
    isActive: string,
}


// 농장 정보
export interface FarmData {
    overwintering: string;
    averageTemperature: string;
    minTemperature: string;
    maxTemperature: string;
    annualRainfall: string;
    sunlightHours: string;
    drainage: string;
    soilDepth: string;
    slopeDegree: string;
    soilTexture: string;
    ph: string;
    organicMatterGPerKg: string;
    avPMgPerKg: string;
    kmgPerKg: string;
    caMgPerKg: string;
    mgMgPerKg: string;
}


export interface NotificationResponseDto {
    notifications: UserNotification[],
    conditions: PriceAlertCondition[],
}


export interface UserNotification {
    id: number, // 
    title: string, // 가격/특보 알림
    content: string; // 어디어디 얼마 
    isRead: string; // T/F
    createDate: string; // 알림 생성일
}