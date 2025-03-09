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
    myFarm: FarmData,
    recommendedProducts: RecommendedProducts[],
    notificationConditions: NotificationCondition[],
}

// 내 농장 추천 상품
export interface RecommendedProducts {
    productName: string,
    reason: string,
}


// 알림 조건 목록
export interface NotificationCondition {
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
    kMgPerKg: string;
    caMgPerKg: string;
    mgMgPerKg: string;
}