// 상품 가격 응답 객체
export interface PriceResponse {
    annual: Price[], // 연간 가격 추이
    monthly: Price[], // 월간 가겨 추이
    daily: Price[], // 일간 가격 추이
    realTime: Price[], // 실시간 경락가
}

// 가격 객체
export interface Price {
    type: "trend" | "real-time"; // 추이 or 실시간
    timeFrame: "yearly" | "monthly" | "daily" | "hourly"; // 년/월/일/시간 단위
    product: string;  // 품목명 
    variety: string;  // 품종명 
    region: string;   // 지역명 
    price: number;    // 가격
    timestamp: string; // 가격이 기록된 시간 (YYYY-MM-DD HH:mm)
}