// 상품 가격 응답 객체
export interface PriceResponse {
    annual: Price[];          // 연간 가격 추이
    monthly: Price[];         // 월간 가격 추이
    daily: Price[];           // 일간 가격 추이
    realTime: Price[];        // 실시간 경락가  – 각 항목에 품목, 품종 정보 포함
    qualityChartData: QualityChartDataItem[];  // 품질별 가격 (30일) – 각 항목에 품종 정보 포함
    regionalChartData: RegionalChartDataItem[]; // 지역별 품종 가격 (특정기간 평균값) – 각 항목에 품종 정보 포함
    sankeyData: SankeyData;   // Sankey 차트 데이터 (산지 -> 품종 -> 도매시장)
  }
  
  // 가격 객체
  export interface Price { 
    id: number; // 데이터 인덱스
    variety: string;  // 품종명 
    price: number;    // 가격
    dateTime: string; // 발생시간
    market?: string; // 시장
  }
  
  // 수정된 품질별 가격 데이터 타입 – 각 항목에 품종 정보를 포함
  export interface QualityChartDataItem {
    date: string;   // "YYYY-MM-DD" 형식의 날짜
    variety: string; // 품종명 (예: "홍옥", "부사", "아오리")
    special?: number;     // "특" 등급 가격
    high?: number;     // "상" 등급 가격
    moderate?: number;   // "보통" 등급 가격
    other?: number;   // "등외" 등급 가격
  }
  
  // 지역별 품종 가격 데이터 타입
  export interface RegionalChartDataItem {
    variety: string; // 품종명 (홍옥, 부사, 아오리 등)
    region: string;  // 지역명 ( 광역시도 )
    price: number;   // 해당 지역의 평균 가격
  }
  
  // 전체 Sankey 데이터 구조
  export interface SankeyData {
    nodes: SankeyNode[]; // 노드 데이터 
    links: SankeyLink[];
  }
  
  // 각 노드를 나타내는 인터페이스
  export interface SankeyNode {
    name: string;  
  }
    
  // 노드 간의 연결(링크)을 나타내는 인터페이스
  export interface SankeyLink {
    source: number; // source 노드의 인덱스 (또는 ID)
    target: number; // target 노드의 인덱스 (또는 ID)
    value: number;  // 해당 연결의 값 (예: 거래량)
  }