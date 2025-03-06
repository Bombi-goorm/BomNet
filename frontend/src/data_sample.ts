import { InterestItem } from "./components/myinfo/InterestsList";
import { PriceAlertItem } from "./components/myinfo/PriceAlertList";
import { HomeDto } from "./types/home_types";
import { Price, PriceResponse } from "./types/price_types";
import { CultivationInfo, Product } from "./types/product_types";

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
            { date: "2025-02-20", price: 8537 },
            { date: "2025-02-21", price: 8400 },
            { date: "2025-02-22", price: 8600 },
            { date: "2025-02-23", price: 8600 },
            { date: "2025-02-24", price: 8600 },
            { date: "2025-02-25", price: 8600 },
            { date: "2025-02-26", price: 8600 },
          ],
        },
        {
          productId: 2,
          productName: "사과",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/apple.jpg",
          dayPrice: [
            { date: "2025-02-20", price: 2000 },
            { date: "2025-02-21", price: 2050 },
            { date: "2025-02-22", price: 2100 },
            { date: "2025-02-23", price: 8600 },
            { date: "2025-02-24", price: 8600 },
            { date: "2025-02-25", price: 8600 },
            { date: "2025-02-26", price: 8600 },
          ],
        },
        {
          productId: 3,
          productName: "배",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/pear.jpg",
          dayPrice: [
            { date: "2025-02-20", price: 8000 },
            { date: "2025-02-21", price: 7900 },
            { date: "2025-02-22", price: 8100 },
            { date: "2025-02-23", price: 8600 },
            { date: "2025-02-24", price: 8600 },
            { date: "2025-02-25", price: 8600 },
            { date: "2025-02-26", price: 8600 },
          ],
        },
        {
          productId: 4,
          productName: "감자",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/potato.jpg",
          dayPrice: [
            { date: "2025-02-20", price: 2000 },
            { date: "2025-02-21", price: 1950 },
            { date: "2025-02-22", price: 2100 },
            { date: "2025-02-23", price: 8600 },
            { date: "2025-02-24", price: 8600 },
            { date: "2025-02-25", price: 8600 },
            { date: "2025-02-26", price: 8600 },
          ],
        },
        {
          productId: 5,
          productName: "고구마",
          imgUrl:
            "https://your-s3-bucket.s3.amazonaws.com/products/gogu.jpg",
          dayPrice: [
            { date: "2025-02-20", price: 5000 },
            { date: "2025-02-21", price: 5200 },
            { date: "2025-02-22", price: 4800 },
            { date: "2025-02-23", price: 8600 },
            { date: "2025-02-24", price: 8600 },
            { date: "2025-02-25", price: 8600 },
            { date: "2025-02-26", price: 8600 },
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



interface Region {
  regionId: number;
  name: string;
}

// 지역 데이터
export const REGIONS: Region[] = [
  { regionId: 1, name: "서울" },
  { regionId: 2, name: "경기" },
  { regionId: 3, name: "인천" },
  { regionId: 4, name: "대전" },
  { regionId: 5, name: "광주" },
  { regionId: 6, name: "대구" },
  { regionId: 7, name: "울산" },
  { regionId: 8, name: "부산" },
  { regionId: 9, name: "강원" },
  { regionId: 10, name: "제주" },
];

// 품목 및 품종 매핑 데이터셋
export const ITEM_VARIETY_MAP = [
  { bigId: '06', bigName: '과실류', midName: "사과", midId: '01', smallName: "홍옥", smallId: '01' },
  { bigId: '06', bigName: '과실류', midName: "사과", midId: '01', smallName: "후지", smallId: '03' },
  { bigId: '06', bigName: '과실류', midName: "사과", midId: '01', smallName: "아오리", smallId: '04' },
  { bigId: '10', bigName: '엽경채류', midName: "배추", midId: '01', smallName: "고랭지배추", smallId: '05' },
  { bigId: '10', bigName: '엽경채류', midName: "배추", midId: '01', smallName: "월동배추", smallId: '04' },
  { bigId: '10', bigName: '엽경채류', midName: "상추", midId: '01', smallName: "청상추", smallId: '01' },
  { bigId: '10', bigName: '엽경채류', midName: "상추", midId: '01', smallName: "적상추", smallId: '02' },
  { bigId: '12', bigName: '조미채소류', midName: "양파", midId: '01', smallName: "양파(일반)", smallId: '01' },
  { bigId: '12', bigName: '조미채소류', midName: "양파", midId: '01', smallName: "양파(수입)", smallId: '98' },
  { bigId: '13', bigName: '양채류', midName: "파프리카", midId: '26', smallName: "파프리카", smallId: '00' },
  { bigId: '26', bigName: '관엽식물류', midName: "아스파라거스", midId: '28', smallName: "아스파라거스", smallId: '00' },
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



// 추천 생산품
export const recommendedProducts = [
  { id: 1, name: "사과", reason: "추천" },
  { id: 2, name: "배", reason: "추천" },
  { id: 3, name: "딸기", reason: "추천" },
  { id: 4, name: "감자", reason: "유통량 상승" },
  { id: 5, name: "고구마", reason: "가격 상승" },
];


// 내 농장 정보
export const farmData = {
  soilType: "양토",
  chemData: "마그네슘 풍부",
  phyData: "배수불량",
  averageTemperature: "10°C",
  annualRainfall: "1200mm",
  tradeAmount: "월 550T",
};


// 추천 지역 거래 정보 샘플 데이터
export const regionStats = [
  { id: 1, region: "서울", tradeVolume: 500, tradeValue: 2000000000 }, // 20억 원
  { id: 2, region: "경기", tradeVolume: 400, tradeValue: 1500000000 }, // 15억 원
  { id: 3, region: "일본", tradeVolume: 200, tradeValue: 800000000 }, // 8억 원
];


// 사용자 정보 데이터
export const user = {
  email: "john.doe@example.com",
  pnuCode: "0987654321",
  joinDate: "2023-01-01",
};

// 관심 품목 데이터
export const interestItems: InterestItem[] = [
  { name: "사과", variety: "후지", isSelected: false },
  { name: "사과", variety: "혹옥", isSelected: true },
  { name: "사과", variety: "아오리", isSelected: false },
  { name: "배추", variety: "월동배추", isSelected: false },
  { name: "배추", variety: "고랭지배추", isSelected: true },
  { name: "양파", variety: "양파(일반)", isSelected: false },
  { name: "파프리카", variety: "파프리카", isSelected: false },
  { name: "아스파라거스", variety: "아스파라거스", isSelected: false },
];

// 가격 알림 데이터 (품목, 품종, 가격 정보 포함)
export const priceAlertItems: PriceAlertItem[] = [
  { name: "사과", variety: "후지", markets: "가락시장", price: "1,200원" },
  { name: "사과", variety: "홍옥", markets: "가락시장, 강서시장", price: "900원" },
  { name: "배추", variety: "고랭지배추", markets: "안양시장", price: "3,500원" },
  { name: "상추", variety: "적상추", markets: "강릉시장", price: "2,000원" },
  { name: "아스파라거스", variety: "아스파라거스", markets: "포항시장, 창원시장, 울산시장", price: "1,500원" },
];


// 상품 정보 데이터
export const sampleProductData: Product[] = [
  {
    productId: 1,
    bigId: 100,
    bigName: "과일",
    midId: 101,
    midName: "사과",
    smallId: 102,
    smallName: "홍옥",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/apple_hongok.jpg"
  },
  {
    productId: 2,
    bigId: 100,
    bigName: "과일",
    midId: 101,
    midName: "사과",
    smallId: 103,
    smallName: "후지",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/apple_fuji.jpg"
  },
  {
    productId: 3,
    bigId: 100,
    bigName: "과일",
    midId: 101,
    midName: "사과",
    smallId: 104,
    smallName: "아오리",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/apple_aori.jpg"
  },
  {
    productId: 4,
    bigId: 200,
    bigName: "채소",
    midId: 201,
    midName: "배추",
    smallId: 202,
    smallName: "월동배추",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/cabbage_winter.jpg"
  },
  {
    productId: 5,
    bigId: 200,
    bigName: "채소",
    midId: 201,
    midName: "배추",
    smallId: 203,
    smallName: "고랭지배추",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/cabbage_highland.jpg"
  },
  {
    productId: 6,
    bigId: 300,
    bigName: "채소",
    midId: 301,
    midName: "양파",
    smallId: 302,
    smallName: "양파(일반)",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/onion_normal.jpg"
  },
  {
    productId: 7,
    bigId: 300,
    bigName: "채소",
    midId: 301,
    midName: "양파",
    smallId: 303,
    smallName: "양파(수입)",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/onion_import.jpg"
  },
  {
    productId: 8,
    bigId: 400,
    bigName: "채소",
    midId: 401,
    midName: "상추",
    smallId: 402,
    smallName: "적상추",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/lettuce_red.jpg"
  },
  {
    productId: 9,
    bigId: 500,
    bigName: "채소",
    midId: 501,
    midName: "파프리카",
    smallId: 502,
    smallName: "파프리카",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/paprika.jpg"
  },
  {
    productId: 10,
    bigId: 600,
    bigName: "채소",
    midId: 601,
    midName: "아스파라거스",
    smallId: 602,
    smallName: "녹색",
    imgUrl: "https://s3.amazonaws.com/my-bucket/products/asparagus_green.jpg"
  }
];

// 재배 정보 데이터
export const sampleCultivationData: CultivationInfo[] = [
  {
    productId: 1,
    cropName: "사과",
    variety: "홍옥",
    conditions: {
      overwintering: "T", // 월동 가능
      avgTemperatureC: "10.0",
      minTemperatureC: "-20.0",
      maxTemperatureC: "35.0",
      annualRainfallMM: "1100.0",
      sunlightHours: "2000.0",
      drainage: "양호",
      soilDepth: "80.0",
      pH: "6.2"
    },
    cultivationFeatures:
      "온대성 과수로서 15-24°C가 적정 생육온도이며, 겨울철 -20°C 이하에서도 내한성이 강한 품종이 있음. 개화기에는 서리 피해에 주의해야 하며, 일조량이 많을수록 착색이 우수함.",
    managementTips:
      "겨울 전정으로 착과 수를 조절하고, 여름철 가지 유인을 통해 통풍과 광합성을 극대화해야 함. 병충해 방지를 위해 적절한 방제 및 봉지 씌우기에 신경 써야 함."
  },
  {
    productId: 4,
    cropName: "배추",
    variety: "월동배추",
    conditions: {
      overwintering: "T",
      avgTemperatureC: "4.0",
      minTemperatureC: "-15.0",
      maxTemperatureC: "22.0",
      annualRainfallMM: "900.0",
      sunlightHours: "1600.0",
      drainage: "양호-약간양호",
      soilDepth: "50.0",
      pH: "6.5"
    },
    cultivationFeatures:
      "냉량성 작물로, 월동배추는 -10°C 이하에서도 생육 가능. 발아 적온은 20-25°C이며, 고온기(여름철)에는 생육이 억제될 수 있음.",
    managementTips:
      "충분한 시비 관리로 결구율을 높이고, 생리장해 예방 필요. 진딧물 및 노린재 방제 필수. 장마철 습해 방지를 위해 배수로 정비 철저."
  },
  {
    productId: 6,
    cropName: "양파",
    variety: "양파(일반)",
    conditions: {
      overwintering: "F",
      avgTemperatureC: "12.0",
      minTemperatureC: "-3.0",
      maxTemperatureC: "30.0",
      annualRainfallMM: "1000.0",
      sunlightHours: "1600.0",
      drainage: "양호-약간불량",
      soilDepth: "50.0",
      pH: "6.2"
    },
    cultivationFeatures:
      "온대 및 온난 지역에서 재배 가능하며, 발아 적온은 15-25°C. 생육 최적 온도는 17°C이며, 배수가 잘되는 토양이 필요함.",
    managementTips:
      "생육 초기에 충분한 관수 필요. 파밤나방, 총채벌레 방제 필수. 수확 후 0-4°C의 저온 저장 필요."
  },
  {
    productId: 8,
    cropName: "상추",
    variety: "적상추",
    conditions: {
      overwintering: "F",
      avgTemperatureC: "16.5",
      minTemperatureC: "0.0",
      maxTemperatureC: "28.0",
      annualRainfallMM: "1100.0",
      sunlightHours: "1750.0",
      drainage: "양호-약간양호",
      soilDepth: "50.0",
      pH: "6.3"
    },
    cultivationFeatures:
      "냉량성 작물로 15-20°C에서 생육이 가장 활발하며, 30°C 이상에서는 생육 저해 및 화아분화(꽃대 올라옴) 발생.",
    managementTips:
      "여름철 고온기에는 차광망을 이용한 온도 조절이 필수. 수확 전 질소 과다 시비를 피해야 질산염 농도 증가 방지 가능. 진딧물 및 노린재 방제 필요."
  },
  {
    productId: 9,
    cropName: "파프리카",
    variety: "파프리카",
    conditions: {
      overwintering: "F",
      avgTemperatureC: "19.5",
      minTemperatureC: "5.0",
      maxTemperatureC: "32.0",
      annualRainfallMM: "1300.0",
      sunlightHours: "2200.0",
      drainage: "양호-약간양호",
      soilDepth: "50.0",
      pH: "6.1"
    },
    cultivationFeatures:
      "재배 기간이 길며, 토양 pH 6.0-6.5에서 잘 자라고, 배수가 잘 되는 유기물 함량이 높은 토양이 적합함.",
    managementTips:
      "병해충(흰가루병, 탄저병) 예방을 위해 환기 및 습도 조절 필수. 적절한 줄기 유인 및 가지치기로 광합성 효율을 극대화해야 함."
  },
  {
    productId: 10,
    cropName: "아스파라거스",
    variety: "녹색",
    conditions: {
      overwintering: "T",
      avgTemperatureC: "15.5",
      minTemperatureC: "-5.0",
      maxTemperatureC: "30.0",
      annualRainfallMM: "1200.0",
      sunlightHours: "2000.0",
      drainage: "양호-약간양호",
      soilDepth: "50.0",
      pH: "6.5"
    },
    cultivationFeatures:
      "다년생 작물로, 첫 수확까지 2-3년이 필요하며, 최적 생육 온도는 16-20°C.",
    managementTips:
      "봄철 새순이 나오기 전에는 덮개 관리 및 보온이 중요. 과습에 약하므로 배수 관리를 철저히 해야 함."
  }
];


// 가격 샘플 데이터
export const priceResponse: PriceResponse = {
  annual: [
    // 홍옥
    { id: 1, variety: "홍옥", dateTime: "2015", price: 2513 },
    { id: 2, variety: "홍옥", dateTime: "2016", price: 2345 },
    { id: 3, variety: "홍옥", dateTime: "2017", price: 2100 },
    { id: 4, variety: "홍옥", dateTime: "2018", price: 2087 },
    { id: 5, variety: "홍옥", dateTime: "2019", price: 2087 },
    { id: 6, variety: "홍옥", dateTime: "2020", price: 2087 },
    { id: 7, variety: "홍옥", dateTime: "2021", price: 2087 },
    { id: 8, variety: "홍옥", dateTime: "2022", price: 2087 },
    { id: 9, variety: "홍옥", dateTime: "2023", price: 2500 },
    { id: 10, variety: "홍옥", dateTime: "2024", price: 2700 },

    // 아오리
    { id: 11, variety: "아오리", dateTime: "2015", price: 2800 },
    { id: 12, variety: "아오리", dateTime: "2016", price: 2650 },
    { id: 13, variety: "아오리", dateTime: "2017", price: 2400 },
    { id: 14, variety: "아오리", dateTime: "2018", price: 2300 },
    { id: 15, variety: "아오리", dateTime: "2019", price: 2250 },
    { id: 16, variety: "아오리", dateTime: "2020", price: 2200 },
    { id: 17, variety: "아오리", dateTime: "2021", price: 2225 },
    { id: 18, variety: "아오리", dateTime: "2022", price: 2300 },
    { id: 19, variety: "아오리", dateTime: "2023", price: 2900 },
    { id: 20, variety: "아오리", dateTime: "2024", price: 3100 },

    // 후지
    { id: 21, variety: "후지", dateTime: "2015", price: 4000 },
    { id: 22, variety: "후지", dateTime: "2016", price: 3900 },
    { id: 23, variety: "후지", dateTime: "2017", price: 3700 },
    { id: 24, variety: "후지", dateTime: "2018", price: 3600 },
    { id: 25, variety: "후지", dateTime: "2019", price: 3550 },
    { id: 26, variety: "후지", dateTime: "2020", price: 3500 },
    { id: 27, variety: "후지", dateTime: "2021", price: 3550 },
    { id: 28, variety: "후지", dateTime: "2022", price: 3650 },
    { id: 29, variety: "후지", dateTime: "2023", price: 4200 },
    { id: 30, variety: "후지", dateTime: "2024", price: 4400 },
  ],
  monthly: [
    // 홍옥
    { id: 1, variety: "홍옥", dateTime: "2024-03", price: 2750 },
    { id: 2, variety: "홍옥", dateTime: "2024-04", price: 2631 },
    { id: 3, variety: "홍옥", dateTime: "2024-05", price: 2513 },
    { id: 4, variety: "홍옥", dateTime: "2024-06", price: 2345 },
    { id: 5, variety: "홍옥", dateTime: "2024-07", price: 2100 },
    { id: 6, variety: "홍옥", dateTime: "2024-08", price: 2087 },
    { id: 7, variety: "홍옥", dateTime: "2024-09", price: 2087 },
    { id: 8, variety: "홍옥", dateTime: "2024-10", price: 2087 },
    { id: 9, variety: "홍옥", dateTime: "2024-11", price: 2087 },
    { id: 10, variety: "홍옥", dateTime: "2024-12", price: 2087 },
    { id: 11, variety: "홍옥", dateTime: "2025-01", price: 2500 },
    { id: 12, variety: "홍옥", dateTime: "2025-02", price: 2700 },

    // 아오리
    { id: 13, variety: "아오리", dateTime: "2024-03", price: 2900 },
    { id: 14, variety: "아오리", dateTime: "2024-04", price: 2785 },
    { id: 15, variety: "아오리", dateTime: "2024-05", price: 2650 },
    { id: 16, variety: "아오리", dateTime: "2024-06", price: 2500 },
    { id: 17, variety: "아오리", dateTime: "2024-07", price: 2300 },
    { id: 18, variety: "아오리", dateTime: "2024-08", price: 2250 },
    { id: 19, variety: "아오리", dateTime: "2024-09", price: 2200 },
    { id: 20, variety: "아오리", dateTime: "2024-10", price: 2250 },
    { id: 21, variety: "아오리", dateTime: "2024-11", price: 2300 },
    { id: 22, variety: "아오리", dateTime: "2024-12", price: 2350 },
    { id: 23, variety: "아오리", dateTime: "2025-01", price: 2800 },
    { id: 24, variety: "아오리", dateTime: "2025-02", price: 3100 },

    // 후지
    { id: 25, variety: "후지", dateTime: "2024-03", price: 4200 },
    { id: 26, variety: "후지", dateTime: "2024-04", price: 4100 },
    { id: 27, variety: "후지", dateTime: "2024-05", price: 3900 },
    { id: 28, variety: "후지", dateTime: "2024-06", price: 3800 },
    { id: 29, variety: "후지", dateTime: "2024-07", price: 3650 },
    { id: 30, variety: "후지", dateTime: "2024-08", price: 3600 },
    { id: 31, variety: "후지", dateTime: "2024-09", price: 3550 },
    { id: 32, variety: "후지", dateTime: "2024-10", price: 3600 },
    { id: 33, variety: "후지", dateTime: "2024-11", price: 3650 },
    { id: 34, variety: "후지", dateTime: "2024-12", price: 3700 },
    { id: 35, variety: "후지", dateTime: "2025-01", price: 4150 },
    { id: 36, variety: "후지", dateTime: "2025-02", price: 4400 },
  ],
  daily: [
    // 홍옥 데이터
    { id: 1, variety: "홍옥", dateTime: "2025-02-21", price: 2100 },
    { id: 2, variety: "홍옥", dateTime: "2025-02-22", price: 2210 },
    { id: 3, variety: "홍옥", dateTime: "2025-02-23", price: 2220 },
    { id: 4, variety: "홍옥", dateTime: "2025-02-24", price: 2330 },
    { id: 5, variety: "홍옥", dateTime: "2025-02-25", price: 2140 },
    { id: 6, variety: "홍옥", dateTime: "2025-02-26", price: 2450 },
    { id: 7, variety: "홍옥", dateTime: "2025-02-27", price: 2660 },
    { id: 8, variety: "홍옥", dateTime: "2025-02-28", price: 2270 },
    { id: 9, variety: "홍옥", dateTime: "2025-03-01", price: 2380 },
    { id: 10, variety: "홍옥", dateTime: "2025-03-02", price: 2990 },
    { id: 11, variety: "홍옥", dateTime: "2025-03-03", price: 2600 },
    { id: 12, variety: "홍옥", dateTime: "2025-03-04", price: 2510 },
    { id: 13, variety: "홍옥", dateTime: "2025-03-05", price: 2420 },
    { id: 14, variety: "홍옥", dateTime: "2025-03-06", price: 2330 },
    { id: 15, variety: "홍옥", dateTime: "2025-03-07", price: 2740 },
  
    // 아오리 데이터
    { id: 16, variety: "아오리", dateTime: "2025-02-21", price: 3500 },
    { id: 17, variety: "아오리", dateTime: "2025-02-22", price: 3110 },
    { id: 18, variety: "아오리", dateTime: "2025-02-23", price: 3220 },
    { id: 19, variety: "아오리", dateTime: "2025-02-24", price: 3130 },
    { id: 20, variety: "아오리", dateTime: "2025-02-25", price: 3340 },
    { id: 21, variety: "아오리", dateTime: "2025-02-26", price: 3050 },
    { id: 22, variety: "아오리", dateTime: "2025-02-27", price: 3860 },
    { id: 23, variety: "아오리", dateTime: "2025-02-28", price: 3770 },
    { id: 24, variety: "아오리", dateTime: "2025-03-01", price: 3180 },
    { id: 25, variety: "아오리", dateTime: "2025-03-02", price: 3490 },
    { id: 26, variety: "아오리", dateTime: "2025-03-03", price: 3200 },
    { id: 27, variety: "아오리", dateTime: "2025-03-04", price: 3410 },
    { id: 28, variety: "아오리", dateTime: "2025-03-05", price: 3520 },
    { id: 29, variety: "아오리", dateTime: "2025-03-06", price: 3630 },
    { id: 30, variety: "아오리", dateTime: "2025-03-07", price: 3140 },
  
    // 후지 데이터
    { id: 31, variety: "후지", dateTime: "2025-02-21", price: 4900 },
    { id: 32, variety: "후지", dateTime: "2025-02-22", price: 4510 },
    { id: 33, variety: "후지", dateTime: "2025-02-23", price: 4120 },
    { id: 34, variety: "후지", dateTime: "2025-02-24", price: 4230 },
    { id: 35, variety: "후지", dateTime: "2025-02-25", price: 4140 },
    { id: 36, variety: "후지", dateTime: "2025-02-26", price: 4250 },
    { id: 37, variety: "후지", dateTime: "2025-02-27", price: 4660 },
    { id: 38, variety: "후지", dateTime: "2025-02-28", price: 4770 },
    { id: 39, variety: "후지", dateTime: "2025-03-01", price: 4680 },
    { id: 40, variety: "후지", dateTime: "2025-03-02", price: 4890 },
    { id: 41, variety: "후지", dateTime: "2025-03-03", price: 4500 },
    { id: 42, variety: "후지", dateTime: "2025-03-04", price: 4610 },
    { id: 43, variety: "후지", dateTime: "2025-03-05", price: 4420 },
    { id: 44, variety: "후지", dateTime: "2025-03-06", price: 4630 },
    { id: 45, variety: "후지", dateTime: "2025-03-07", price: 4940 },
  ],
  realTime: [
    { id: 1, variety: "홍옥", dateTime: "2025-02-22T08:00", price: 4767 },
    { id: 2, variety: "홍옥", dateTime: "2025-02-22T09:00", price: 4672 },
    { id: 3, variety: "홍옥", dateTime: "2025-02-22T10:00", price: 3684 },
    { id: 4, variety: "홍옥", dateTime: "2025-02-22T11:00", price: 3791 },
    { id: 5, variety: "홍옥", dateTime: "2025-02-22T12:00", price: 4096 },
    { id: 6, variety: "홍옥", dateTime: "2025-02-22T13:00", price: 3802 },
    { id: 7, variety: "아오리", dateTime: "2025-02-22T08:00", price: 4280 },
    { id: 8, variety: "아오리", dateTime: "2025-02-22T09:00", price: 3056 },
    { id: 9, variety: "아오리", dateTime: "2025-02-22T10:00", price: 3954 },
    { id: 10, variety: "아오리", dateTime: "2025-02-22T11:00", price: 3652 },
    { id: 11, variety: "아오리", dateTime: "2025-02-22T12:00", price: 4889 },
    { id: 12, variety: "아오리", dateTime: "2025-02-22T13:00", price: 4356 },
    { id: 13, variety: "후지", dateTime: "2025-02-22T08:00", price: 4931 },
    { id: 14, variety: "후지", dateTime: "2025-02-22T09:00", price: 3458 },
    { id: 15, variety: "후지", dateTime: "2025-02-22T10:00", price: 3599 },
    { id: 16, variety: "후지", dateTime: "2025-02-22T11:00", price: 4157 },
    { id: 17, variety: "후지", dateTime: "2025-02-22T12:00", price: 3928 },
    { id: 18, variety: "후지", dateTime: "2025-02-22T13:00", price: 4860 },
  ],
  qualityChartData: [
    // 홍옥 데이터
    { date: "2025-02-28", variety: "홍옥", 특: 5000, 상: 4500, 보통: 4000, 등외: 3500 },
    { date: "2025-03-01", variety: "홍옥", 특: 5100, 상: 4550, 보통: 4050, 등외: 3550 },
    { date: "2025-03-02", variety: "홍옥", 특: 5200, 상: 4600, 보통: 4100, 등외: 3600 },
    { date: "2025-03-03", variety: "홍옥", 특: 5150, 상: 4570, 보통: 4070, 등외: 3570 },
    { date: "2025-03-04", variety: "홍옥", 특: 5250, 상: 4620, 보통: 4120, 등외: 3620 },
    // 아오리 데이터
    { date: "2025-02-28", variety: "아오리", 특: 5300, 상: 4650, 보통: 4150, 등외: 3650 },
    { date: "2025-03-01", variety: "아오리", 특: 5350, 상: 4700, 보통: 4200, 등외: 3700 },
    { date: "2025-03-02", variety: "아오리", 특: 5400, 상: 4750, 보통: 4250, 등외: 3750 },
    { date: "2025-03-03", variety: "아오리", 특: 5450, 상: 4800, 보통: 4300, 등외: 3800 },
    { date: "2025-03-04", variety: "아오리", 특: 5500, 상: 4850, 보통: 4350, 등외: 3850 },
    // 후지 데이터
    { date: "2025-02-28", variety: "후지", 특: 6000, 상: 5500, 보통: 5000, 등외: 4500 },
    { date: "2025-03-01", variety: "후지", 특: 6050, 상: 5550, 보통: 5050, 등외: 4550 },
    { date: "2025-03-02", variety: "후지", 특: 6100, 상: 5600, 보통: 5100, 등외: 4600 },
    { date: "2025-03-03", variety: "후지", 특: 6150, 상: 5650, 보통: 5150, 등외: 4650 },
    { date: "2025-03-04", variety: "후지", 특: 6200, 상: 5700, 보통: 5200, 등외: 4700 },
  ],
  regionalChartData: [
    { variety: "홍옥", region: "서울", price: 4800 },
    { variety: "홍옥", region: "대전", price: 4700 },
    { variety: "홍옥", region: "부산", price: 4750 },
    { variety: "홍옥", region: "대구", price: 4650 },
    { variety: "홍옥", region: "광주", price: 4750 },
    { variety: "홍옥", region: "울산", price: 4700 },
    { variety: "홍옥", region: "인천", price: 4650 },
    { variety: "아오리", region: "서울", price: 4900 },
    { variety: "아오리", region: "대전", price: 4800 },
    { variety: "아오리", region: "부산", price: 4850 },
    { variety: "아오리", region: "대구", price: 4750 },
    { variety: "아오리", region: "광주", price: 4850 },
    { variety: "후지", region: "서울", price: 5500 },
    { variety: "후지", region: "대전", price: 5400 },
    { variety: "후지", region: "부산", price: 5450 },
    { variety: "후지", region: "대구", price: 5350 },
    { variety: "후지", region: "광주", price: 5550 },
    { variety: "후지", region: "울산", price: 5600 },
    { variety: "후지", region: "인천", price: 5450 },
  ],
  sankeyData: {
    nodes: [
      // 첫 번째 레이어: 품종
      { name: '홍옥' },  // 0
      { name: '아오리' }, // 1
      { name: '후지' },  // 2
      // 두 번째 레이어: 생산지
      { name: '경북' },  // 3
      { name: '충북' },  // 4
      { name: '경남' },  // 5
      { name: '전북' },  // 6
      { name: '전남' },  // 7
      // 세 번째 레이어: 도매시장
      { name: '안양도매시장' },    // 8
      { name: '대구북부도매' },    // 9
      { name: '포항도매시장' },    // 10
      { name: '서울가락도매시장' }, // 11
      { name: '부산도매시장' },     // 12
      { name: '대구중앙도매시장' }, // 13
      { name: '인천도매시장' },     // 14
    ],
    links: [
      // Set 1: 품종 -> 기존 생산지 (경북, 충북, 경남)
      { source: 0, target: 3, value: 200 },  // 홍옥 → 경북
      { source: 0, target: 4, value: 100 },  // 홍옥 → 충북
      { source: 0, target: 5, value: 50 },   // 홍옥 → 경남
      { source: 1, target: 3, value: 150 },  // 아오리 → 경북
      { source: 1, target: 4, value: 120 },  // 아오리 → 충북
      { source: 1, target: 5, value: 100 },  // 아오리 → 경남
      { source: 2, target: 3, value: 80 },   // 후지 → 경북
      { source: 2, target: 4, value: 90 },   // 후지 → 충북
      { source: 2, target: 5, value: 110 },  // 후지 → 경남
  
      // Set 2: 품종 -> 신규 생산지 (전북, 전남)
      { source: 0, target: 6, value: 70 },  // 홍옥 → 전북
      { source: 0, target: 7, value: 30 },  // 홍옥 → 전남
      { source: 1, target: 6, value: 90 },  // 아오리 → 전북
      { source: 1, target: 7, value: 50 },  // 아오리 → 전남
      { source: 2, target: 6, value: 60 },  // 후지 → 전북
      { source: 2, target: 7, value: 80 },  // 후지 → 전남
  
      // Set 3: 기존 생산지 -> 기존 도매시장
      { source: 3, target: 8, value: 120 },  // 경북 → 안양도매시장
      { source: 3, target: 9, value: 60 },   // 경북 → 대구북부도매
      { source: 3, target: 10, value: 50 },  // 경북 → 포항도매시장
      { source: 4, target: 11, value: 90 },  // 충북 → 서울가락도매시장
      { source: 4, target: 12, value: 90 },  // 충북 → 부산도매시장
      { source: 5, target: 8, value: 30 },   // 경남 → 안양도매시장
      { source: 5, target: 9, value: 20 },   // 경남 → 대구북부도매
      { source: 5, target: 10, value: 40 },  // 경남 → 포항도매시장
  
      // Set 4: 기존 생산지 -> 신규 도매시장
      { source: 3, target: 13, value: 30 },  // 경북 → 대구중앙도매시장
      { source: 4, target: 14, value: 20 },  // 충북 → 인천도매시장
      { source: 5, target: 13, value: 10 },  // 경남 → 대구중앙도매시장
  
      // Set 5: 신규 생산지 -> 신규 도매시장
      { source: 6, target: 13, value: 50 },  // 전북 → 대구중앙도매시장
      { source: 6, target: 14, value: 40 },  // 전북 → 인천도매시장
      { source: 7, target: 13, value: 30 },  // 전남 → 대구중앙도매시장
      { source: 7, target: 14, value: 20 },  // 전남 → 인천도매시장
    ]
  },
};