// 상품 검색 요청 
export interface ProductRequestDto {
    itemId?: number, // 품목ID
    item?: string, // 품목
    varietyId?: number, // 품종ID
    variety?: string, // 품종
    pnu?: string, // PNU
}


// 상품 응답 Dto
export interface ProductResponseDto {
    product: Product, // 상품 정보
    cultivationInfo: CultivationInfo, // 상품 재배 정보
    farmSuitability: FarmSuitability, // 농장 적합도
}


// 상품 객체(product_code)
export interface Product {
    productId: number,
    categoryId: number,
    category: string,
    itemId: number,
    item: string,
    varietyId: number,
    variety: string,
    imgUrl: string, // S3 이미지 경로
}

// 작물 재배 정보
export interface CultivationInfo {
    productId: number, // 상품ID
    cropName: string; // 작물명
    variety: string | null; // 품종명 (품종이 없으면 null)
    conditions: Conditions; // 생산 조건
    cultivationFeatures: string; // 재배 특징 설명
    cultivationTips: string; // 관리 팁
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


// 농장 적합도
export interface FarmSuitability {
    climateSuitability: SuitabilityResult,
    soilChemicalSuitability: SuitabilityResult,
    soilPhysicalSuitability: SuitabilityResult,
}

// 적합도 분석
export interface SuitabilityResult {
    unsuitableProperties: string[], 
    suitability: string, // 적합도 
}