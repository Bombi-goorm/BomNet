// 상품 검색 요청 
export interface ProductRequestDto {
    startDate?: string, // 검색시작일
    endDate?: string, // 검색종료일
    midId?: number, // 품목ID
    midName?: string, // 품목
    smallId?: number, // 품종ID
    smallName?: string, // 품종
    location?: string, // 지역
    BJD?: string // 법정동코드
}


// 상품 응답 Dto
export interface ProductResponseDto {
    product: Product, // 상품 정보
    plantCultivation: CultivationInfo, // 상품 재배 정보
    sales: ProductSales, // 상품 판매량 
    farmSuitability: FarmSuitability, // 농장 적합도
}


// 상품 객체(product_code)
export interface Product {
    productId: number,
    bigId: number,
    bigName: string,
    midId: number,
    midName: string,
    smallId: number,
    smallName: string,
    imgUrl: string, // S3 이미지 경로
}

// 작물 재배 정보
export interface CultivationInfo {
    productId: number, // 상품ID
    cropName: string; // 작물명
    variety: string | null; // 품종명 (품종이 없으면 null)
    conditions: Conditions; // 생산 조건
    cultivationFeatures: string; // 재배 특징 설명
    managementTips: string; // 관리 팁
  }

// 생산 조건
export interface Conditions {
    overwintering: string;     // 월동 여부 (T: 가능, F: 불가능)
    avgTemperatureC: string;     // 평균 기온 (°C)
    minTemperatureC: string;     // 최저 기온 (°C)
    maxTemperatureC: string;     // 최고 기온 (°C)
    annualRainfallMM: string;     // 연평균 강수량 (mm)
    sunlightHours: string;      // 일조량 (시간)
    drainage: string;           // 배수 등급 ("양호", "불량" 등)
    soilDepth: string;          // 유효 토심 (cm)
    pH: string;                 // 토양 산도 (pH)
}

// 판매량 정보 (올해)
export interface ProductSales {
    distributionQuantityTon: string // 지역별 유통량
    domesticSalesTon: string, // 국내 총 판매량(총 유통량)
    exportsTon: string, // 수출량
    importsTon: string, // 수입량
}

// 농장 적합도
export interface FarmSuitability {
    anayize: Anayize[],
}

// 적합도 분석
export interface Anayize {
    reason: string, 
    // 월동여부
    // 평균기온
    // 최저기온
    // 최고기온
    // 연평균 강수량
    // 일조량
    // 배수등급
    // 유효토심
    // 산도
    suitability: string, // 적합도 
}