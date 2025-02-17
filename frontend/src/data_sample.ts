import { InterestItem } from "./components/myinfo/InterestsList";
import { PriceAlertItem } from "./components/myinfo/PriceAlertList";
import { HomeDto } from "./types/member_types";

// 홈화면 샘플 데이터
export const data: HomeDto =  {
  bestItems:  // 5개
    {
      products: [ //7일
        {
          productId: 1,
          productName: "딸기",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/strawberry.jpg",
          dayPrice: [
            { date: "01/31", price: "8,537원" },
            { date: "02/01", price: "8,400원" },
            { date: "02/02", price: "8,600원" },
            { date: "02/03", price: "8,600원" },
            { date: "02/04", price: "8,600원" },
            { date: "02/05", price: "8,600원" },
            { date: "02/06", price: "8,600원" },
          ],
        },
        {
          productId: 2,
          productName: "사과",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/apple.jpg",
          dayPrice: [
            { date: "01/31", price: "2,000원" },
            { date: "02/01", price: "2,050원" },
            { date: "02/02", price: "2,100원" },
            { date: "02/03", price: "8,600원" },
            { date: "02/04", price: "8,600원" },
            { date: "02/05", price: "8,600원" },
            { date: "02/06", price: "8,600원" },
          ],
        },
        {
          productId: 3,
          productName: "배",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/pear.jpg",
          dayPrice: [
            { date: "01/31", price: "8,000원" },
            { date: "02/01", price: "7,900원" },
            { date: "02/02", price: "8,100원" },
            { date: "02/03", price: "8,600원" },
            { date: "02/04", price: "8,600원" },
            { date: "02/05", price: "8,600원" },
            { date: "02/06", price: "8,600원" },
          ],
        },
        {
          productId: 4,
          productName: "감자",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/potato.jpg",
          dayPrice: [
            { date: "01/31", price: "2,000원" },
            { date: "02/01", price: "1,950원" },
            { date: "02/02", price: "2,100원" },
            { date: "02/03", price: "8,600원" },
            { date: "02/04", price: "8,600원" },
            { date: "02/05", price: "8,600원" },
            { date: "02/06", price: "8,600원" },
          ],
        },
        {
          productId: 5,
          productName: "고구마",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/gogu.jpg",
          dayPrice: [
            { date: "01/31", price: "5,000원" },
            { date: "02/01", price: "5,200원" },
            { date: "02/02", price: "4,800원" },
            { date: "02/03", price: "8,600원" },
            { date: "02/04", price: "8,600원" },
            { date: "02/05", price: "8,600원" },
            { date: "02/06", price: "8,600원" },
          ],
        },
      ],
    },
  weatherNotice: [ // 6개만
    {
      dateTime: "2023-02-01T08:00:00Z",
      location: "서울",
      content: "강풍 주의보 발령",
    },
    {
      dateTime: "2023-02-01T09:00:00Z",
      location: "부산",
      content: "폭우 경보 발령",
    },
    {
      dateTime: "2023-02-01T09:00:00Z",
      location: "부산",
      content: "폭우 경보 발령",
    },
    {
      dateTime: "2023-02-01T09:00:00Z",
      location: "부산",
      content: "폭우 경보 발령",
    },
    {
      dateTime: "2023-02-01T09:00:00Z",
      location: "부산",
      content: "폭우 경보 발령",
    },
    {
      dateTime: "2023-02-01T09:00:00Z",
      location: "부산",
      content: "폭우 경보 발령",
    },
  ],
  weatherExpect:  // 6개
    {
      location: "서울",
      weatherInfo: [
        {
          weather: "맑음",
          temperature: "5°C",
          humidity: "30%",
          wind: "10km/h",
          dateTime: "2023-02-01T12:00:00Z",
        },
        {
          weather: "눈",
          temperature: "-7°C",
          humidity: "75%",
          wind: "2km/h",
          dateTime: "2023-02-01T14:00:00Z",
        },
        {
          weather: "비",
          temperature: "7°C",
          humidity: "35%",
          wind: "8km/h",
          dateTime: "2023-02-01T15:00:00Z",
        },
        {
          weather: "천둥",
          temperature: "3°C",
          humidity: "50%",
          wind: "22km/h",
          dateTime: "2023-02-01T16:00:00Z",
        },
        {
          weather: "맑음",
          temperature: "3°C",
          humidity: "50%",
          wind: "22km/h",
          dateTime: "2023-02-01T16:00:00Z",
        },
        {
          weather: "구름",
          temperature: "3°C",
          humidity: "50%",
          wind: "22km/h",
          dateTime: "2023-02-01T16:00:00Z",
        },
      ],
    },
  news: [
    {
      title: "농업 혁신, 새로운 시대를 열다",
      content: "농업 기술의 발전이 새로운 도약을 예고합니다.",
      dateTime: "2023-02-01T10:00:00Z",
      newsLink: "https://news.example.com/article/1",
    },
    {
      title: "기후 변화, 농업에 미치는 영향",
      content: "기후 변화가 농업에 미치는 영향에 대한 심도 있는 분석.",
      dateTime: "2023-02-01T11:00:00Z",
      newsLink: "https://news.example.com/article/2",
    },
    {
      title: "기후 변화, 농업에 미치는 영향",
      content: "기후 변화가 농업에 미치는 영향에 대한 심도 있는 분석.",
      dateTime: "2023-02-01T11:00:00Z",
      newsLink: "https://news.example.com/article/2",
    },
    {
      title: "기후 변화, 농업에 미치는 영향",
      content: "기후 변화가 농업에 미치는 영향에 대한 심도 있는 분석.",
      dateTime: "2023-02-01T11:00:00Z",
      newsLink: "https://news.example.com/article/2",
    },
  ],
}

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
  { id: 5, name: "고구마", reason: "계절성" },
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

// 사용자 정보 데이터
export const user = {
  email: "john.doe@example.com",
  legalCode: "1234567890",
  pnuCode: "0987654321",
  joinDate: "2023-01-01",
};

// 관심 품목 데이터
export const interestItems: InterestItem[] = [
  { name: "사과", variety: "후지", isSelected: false },
  { name: "바나나", variety: "캐번디시", isSelected: true },
  { name: "포도", variety: "샤인머스캣", isSelected: false },
  { name: "딸기", variety: "설향", isSelected: false },
  { name: "배", variety: "신고", isSelected: true },
  { name: "오렌지", variety: "발렌시아", isSelected: false },
  { name: "수박", variety: "참외", isSelected: false },
  { name: "키위", variety: "헤이즐", isSelected: false },
];

// 가격 알림 데이터 (품목, 품종, 가격 정보 포함)
export const priceAlertItems: PriceAlertItem[] = [
  { name: "사과", variety: "후지", price: "1,200원" },
  { name: "바나나", variety: "캐번디시", price: "900원" },
  { name: "포도", variety: "샤인머스캣", price: "3,500원" },
  { name: "딸기", variety: "설향", price: "2,000원" },
  { name: "배", variety: "신고", price: "1,500원" },
];

