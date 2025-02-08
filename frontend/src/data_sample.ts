// 품목 데이터 (식별자, 이름)
export const items = [
    { id: 1, name: "사과" },
    { id: 2, name: "배" },
    { id: 3, name: "딸기" },
    { id: 4, name: "감자" },
    { id: 5, name: "고구마" },
    { id: 6, name: "양파" },
    { id: 7, name: "오이" },
    { id: 8, name: "당근" },
    { id: 9, name: "호박" },
    { id: 10, name: "토마토" },
  ];
  
  // 품종 데이터 (식별자, 품목 식별자, 이름)
  export const varieties = [
    { id: 1, itemId: 1, name: "부사" },
    { id: 2, itemId: 1, name: "홍로" },
    { id: 3, itemId: 1, name: "아오리" },
    { id: 4, itemId: 2, name: "신고" },
    { id: 5, itemId: 2, name: "원황" },
    { id: 6, itemId: 3, name: "설향" },
    { id: 7, itemId: 3, name: "매향" },
    { id: 8, itemId: 4, name: "대서" },
    { id: 9, itemId: 4, name: "수미" },
    { id: 10, itemId: 5, name: "밤고구마" },
    { id: 11, itemId: 5, name: "호박고구마" },
  ];
  
  // 지역 데이터 (식별자, 이름)
  export const regions = [
    { id: 1, name: "서울" },
    { id: 2, name: "경기" },
    { id: 3, name: "인천" },
    { id: 4, name: "대전" },
    { id: 5, name: "광주" },
    { id: 6, name: "대구" },
    { id: 7, name: "울산" },
    { id: 8, name: "부산" },
    { id: 9, name: "강원" },
    { id: 10, name: "제주" },
  ];
  
  export const prices = [
    { id: 1, varietyId: 1, date: "2025-02-01", regionId: 1, price: 2500 },
    { id: 2, varietyId: 1, date: "2025-02-02", regionId: 1, price: 2700 },
    { id: 3, varietyId: 1, date: "2025-02-03", regionId: 1, price: 2750 },
    { id: 4, varietyId: 1, date: "2025-02-04", regionId: 1, price: 2631 },
    { id: 5, varietyId: 1, date: "2025-02-05", regionId: 1, price: 2513 },
    { id: 6, varietyId: 1, date: "2025-02-06", regionId: 1, price: 2345 },
    { id: 7, varietyId: 1, date: "2025-02-07", regionId: 1, price: 2100 },
    { id: 8, varietyId: 1, date: "2025-02-08", regionId: 1, price: 2087 },

    { id: 9, varietyId: 2, date: "2025-02-01", regionId: 1, price: 2600 },
    { id: 10, varietyId: 2, date: "2025-02-02", regionId: 1, price: 2344 },
    { id: 11, varietyId: 2, date: "2025-02-03", regionId: 1, price: 2198 },
    { id: 12, varietyId: 2, date: "2025-02-04", regionId: 1, price: 3124 },
    { id: 13, varietyId: 2, date: "2025-02-05", regionId: 1, price: 3354 },
    { id: 14, varietyId: 2, date: "2025-02-06", regionId: 1, price: 3022 },
    { id: 15, varietyId: 2, date: "2025-02-07", regionId: 1, price: 2954 },
    { id: 16, varietyId: 2, date: "2025-02-08", regionId: 1, price: 2744 },

    { id: 17, varietyId: 3, date: "2025-02-01", regionId: 1, price: 4522 },
    { id: 18, varietyId: 3, date: "2025-02-02", regionId: 1, price: 4211 },
    { id: 19, varietyId: 3, date: "2025-02-03", regionId: 1, price: 3952 },
    { id: 20, varietyId: 3, date: "2025-02-04", regionId: 1, price: 3755 },
    { id: 21, varietyId: 3, date: "2025-02-05", regionId: 1, price: 3800 },
    { id: 22, varietyId: 3, date: "2025-02-06", regionId: 1, price: 3900 },
    { id: 23, varietyId: 3, date: "2025-02-07", regionId: 1, price: 3911 },
    { id: 24, varietyId: 3, date: "2025-02-08", regionId: 1, price: 4052 },

    { id: 29, varietyId: 4, date: "2025-02-05", regionId: 7, price: 4000 },
    { id: 30, varietyId: 4, date: "2025-02-05", regionId: 8, price: 4100 },
    { id: 31, varietyId: 5, date: "2025-02-06", regionId: 9, price: 2700 },
    { id: 32, varietyId: 5, date: "2025-02-06", regionId: 10, price: 2600 },
    { id: 33, varietyId: 6, date: "2025-02-07", regionId: 1, price: 4500 },
    { id: 34, varietyId: 6, date: "2025-02-07", regionId: 2, price: 4600 },
    { id: 35, varietyId: 7, date: "2025-02-08", regionId: 3, price: 3300 },
    { id: 36, varietyId: 7, date: "2025-02-08", regionId: 4, price: 3400 },
    { id: 37, varietyId: 8, date: "2025-02-02", regionId: 5, price: 4200 },
    { id: 38, varietyId: 9, date: "2025-02-03", regionId: 6, price: 4300 },
    { id: 39, varietyId: 10, date: "2025-02-04", regionId: 7, price: 3000 },
    { id: 40, varietyId: 11, date: "2025-02-05", regionId: 8, price: 3100 },
  ];
  
  // 실시간 경락가 데이터 (id, 품종 식별자, 날짜시간, 지역 식별자, 경락가)
  export const liveAuctionPrices = [
    { id: 1, varietyId: 1, datetime: "2025-02-08T08:00", regionId: 1, price: 4767 },
    { id: 2, varietyId: 1, datetime: "2025-02-08T09:00", regionId: 1, price: 4672 },
    { id: 3, varietyId: 1, datetime: "2025-02-08T10:00", regionId: 1, price: 3684 },
    { id: 4, varietyId: 1, datetime: "2025-02-08T11:00", regionId: 1, price: 3791 },
    { id: 5, varietyId: 1, datetime: "2025-02-08T12:00", regionId: 1, price: 4096 },
    { id: 6, varietyId: 1, datetime: "2025-02-08T13:00", regionId: 1, price: 3802 },
    { id: 7, varietyId: 1, datetime: "2025-02-08T14:00", regionId: 1, price: 4911 },
    { id: 8, varietyId: 1, datetime: "2025-02-08T15:00", regionId: 1, price: 4764 },
    { id: 9, varietyId: 1, datetime: "2025-02-08T16:00", regionId: 1, price: 3490 },
    { id: 10, varietyId: 1, datetime: "2025-02-08T17:00", regionId: 1, price: 4107 },
    { id: 11, varietyId: 2, datetime: "2025-02-08T08:00", regionId: 1, price: 4280 },
    { id: 12, varietyId: 2, datetime: "2025-02-08T09:00", regionId: 1, price: 3056 },
    { id: 13, varietyId: 2, datetime: "2025-02-08T10:00", regionId: 1, price: 3954 },
    { id: 14, varietyId: 2, datetime: "2025-02-08T11:00", regionId: 1, price: 3652 },
    { id: 15, varietyId: 2, datetime: "2025-02-08T12:00", regionId: 1, price: 4889 },
    { id: 16, varietyId: 2, datetime: "2025-02-08T13:00", regionId: 1, price: 4356 },
    { id: 17, varietyId: 2, datetime: "2025-02-08T14:00", regionId: 1, price: 4032 },
    { id: 18, varietyId: 2, datetime: "2025-02-08T15:00", regionId: 1, price: 4901 },
    { id: 19, varietyId: 2, datetime: "2025-02-08T16:00", regionId: 1, price: 4680 },
    { id: 20, varietyId: 2, datetime: "2025-02-08T17:00", regionId: 1, price: 4027 },
    { id: 21, varietyId: 3, datetime: "2025-02-08T08:00", regionId: 1, price: 4931 },
    { id: 22, varietyId: 3, datetime: "2025-02-08T09:00", regionId: 1, price: 3458 },
    { id: 23, varietyId: 3, datetime: "2025-02-08T10:00", regionId: 1, price: 3599 },
    { id: 24, varietyId: 3, datetime: "2025-02-08T11:00", regionId: 1, price: 4157 },
    { id: 25, varietyId: 3, datetime: "2025-02-08T12:00", regionId: 1, price: 3928 },
    { id: 26, varietyId: 3, datetime: "2025-02-08T13:00", regionId: 1, price: 4860 },
    { id: 27, varietyId: 3, datetime: "2025-02-08T14:00", regionId: 1, price: 3290 },
    { id: 28, varietyId: 3, datetime: "2025-02-08T15:00", regionId: 1, price: 4978 },
    { id: 29, varietyId: 3, datetime: "2025-02-08T16:00", regionId: 1, price: 3773 },
    { id: 30, varietyId: 3, datetime: "2025-02-08T17:00", regionId: 1, price: 4146 },
  ];
  
  
// 인기 품목
export const pupularProducts = [
    { id: 1, varietyId: 1},
    { id: 2, varietyId: 2},
    { id: 3, varietyId: 3},
    { id: 4, varietyId: 9},
    { id: 5, varietyId: 10},
]



// ------- 생산품 페이지

// 전국 거래 정보 데이터
export const nationwideTradeInfo = {
  totalVolume: 1400.5, // 거래량 (톤)
  totalAmount: 6477000000, // 거래 금액 (원)
  avgPrice: 3873, // 평균 가격 (원/kg)
  fluctuationRate: 0.4, // 등락률 (%)
};


// 실시간 기상 특보 데이터
export const weatherWarnings = [
  { date: "2025-02-06", time: "13:00", region: "경기", warning: "한파경보" },
  { date: "2025-02-05", time: "13:00", region: "울릉도", warning: "한파경보" },
  { date: "2025-02-04", time: "13:00", region: "경상도", warning: "한파경보" },
  { date: "2025-02-03", time: "13:00", region: "전라도", warning: "한파경보" },
  { date: "2025-02-02", time: "13:00", region: "충청도", warning: "한파경보" },
];


// 추천 생산품
export const recommendedProducts = [
  { id: 1, name: "사과", reason: "토양 적합도 높음" },
  { id: 2, name: "배", reason: "기후 적합도 높음" },
  { id: 3, name: "딸기", reason: "수익성 높음" },
  { id: 4, name: "감자", reason: "지역 수요 높음" },
  { id: 5, name: "고구마", reason: "정부 보조금 대상 품목" },
];


// 내 농장 정보
export const farmData = {
  location: "강원도",
  soilType: "양토",
  averageTemperature: "10°C",
  annualRainfall: "1200mm",
  tradeAmount: "월 550T",
  polices: "일반",
};


// 정부 정책 정보
export const governmentPolicies = {
  subsidies: ["딸기", "마늘", "감자"],
  restrictions: ["바나나", "두리안", "옥수수"],
};

// 추천 지역 거래 정보 샘플 데이터
export const regionStats = [
  { id: 1, region: "서울", tradeVolume: 500, tradeValue: 2000000000 }, // 20억 원
  { id: 2, region: "경기", tradeVolume: 400, tradeValue: 1500000000 }, // 15억 원
  { id: 3, region: "일본", tradeVolume: 200, tradeValue: 800000000 }, // 8억 원
];

// 재배 정보 데이터
export const cultivationInfo = {
  title: "가지 재배 정보",
  description:
    "가지는 고온성 작물로 25-30°C가 적정 생육온도이며, 서리 피해와 저온 피해가 크기 때문에 수확기 후 작형 전환 시기에 신경을 써야 합니다. 재배지역의 일조량은 최소 10시간 이상이어야 합니다. 병충해 예방을 위해서는 적정 온도 유지와 주기적인 방제 작업이 필수입니다.",
    imageUrl: "/src/assets/eggplant.jpg",
  conditions: {
    temperature: "20-25°C",
    soil: "양토",
    rainfall: "1000-1500mm",
    sunlight: "6-8시간",
  },
};


