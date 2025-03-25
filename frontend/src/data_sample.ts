
import { BestItems, HomeDto } from "./types/home_types";
import { FarmData } from "./types/member_types";
import { PriceResponse } from "./types/price_types";
// import { CultivationInfo, FarmSuitability, Product, ProductResponseDto } from "./types/product_types";

import strawberryImg from "./assets/strawberry.jpg";
import appleImg from "./assets/apple.jpg";
import potatoImg from "./assets/potato.jpg";
import pearImg from "./assets/pear.jpg";
import goguImg from "./assets/gogu.jpg";

export const bestItemsFix: BestItems = {
    products: [ //7일
      {
        productId: 1,
        productName: "딸기",
        imgUrl: strawberryImg,
          productPrices: [
          { date: "2025-03-10", price: 6403 },
          { date: "2025-03-11", price: 6202 },
          { date: "2025-03-12", price: 6504 },
          { date: "2025-03-13", price: 6444 },
          { date: "2025-03-14", price: 6868 },
          { date: "2025-03-15", price: 6999 },
          { date: "2025-03-16", price: 6854 },
        ],
      },
      {
        productId: 2,
        productName: "사과",
        imgUrl: appleImg,
          productPrices: [
          { date: "2025-03-10", price: 5000 },
          { date: "2025-03-11", price: 5050 },
          { date: "2025-03-12", price: 5100 },
          { date: "2025-03-13", price: 5600 },
          { date: "2025-03-14", price: 5500 },
          { date: "2025-03-15", price: 5800 },
          { date: "2025-03-16", price: 6600 },
        ],
      },
      {
        productId: 3,
        productName: "배",
        imgUrl: pearImg,
          productPrices: [
          { date: "2025-03-10", price: 3000 },
          { date: "2025-03-11", price: 3900 },
          { date: "2025-03-12", price: 3100 },
          { date: "2025-03-13", price: 3600 },
          { date: "2025-03-14", price: 3600 },
          { date: "2025-03-15", price: 3900 },
          { date: "2025-03-16", price: 4200 },
        ],
      },
      {
        productId: 4,
        productName: "감자",
        imgUrl: potatoImg,
          productPrices: [
          { date: "2025-03-10", price: 2000 },
          { date: "2025-03-11", price: 1950 },
          { date: "2025-03-12", price: 2100 },
          { date: "2025-03-13", price: 2600 },
          { date: "2025-03-14", price: 3600 },
          { date: "2025-03-15", price: 3610 },
          { date: "2025-03-16", price: 3800 },
        ],
      },
      {
        productId: 5,
        productName: "고구마",
        imgUrl: goguImg,
          productPrices: [
          { date: "2025-03-10", price: 2000 },
          { date: "2025-03-11", price: 3200 },
          { date: "2025-03-12", price: 3800 },
          { date: "2025-03-13", price: 2600 },
          { date: "2025-03-14", price: 2600 },
          { date: "2025-03-15", price: 2800 },
          { date: "2025-03-16", price: 2500 },
        ],
      },
    ],
  }

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
            productPrices: [
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
            productPrices: [
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
            productPrices: [
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
            productPrices: [
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
            productPrices: [
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

// 품목 및 품종 매핑 데이터셋
export const ITEM_VARIETY_MAP = [
  { bigId: '06', bigName: '과실류', midName: "사과", midId: '01', smallName: "홍옥", smallId: '01' },
  { bigId: '06', bigName: '과실류', midName: "사과", midId: '01', smallName: "후지", smallId: '03' },
  { bigId: '06', bigName: '과실류', midName: "사과", midId: '01', smallName: "아오리", smallId: '04' },
  { bigId: '10', bigName: '엽경채류', midName: "배추", midId: '01', smallName: "고랭지배추", smallId: '05' },
  { bigId: '10', bigName: '엽경채류', midName: "배추", midId: '01', smallName: "월동배추", smallId: '04' },
  { bigId: '10', bigName: '엽경채류', midName: "상추", midId: '01', smallName: "적상추", smallId: '02' },
  { bigId: '12', bigName: '조미채소류', midName: "양파", midId: '01', smallName: "양파(일반)", smallId: '01' },
  { bigId: '12', bigName: '조미채소류', midName: "양파", midId: '01', smallName: "양파(수입)", smallId: '98' },
  { bigId: '13', bigName: '양채류', midName: "파프리카", midId: '26', smallName: "파프리카", smallId: '00' },
  { bigId: '26', bigName: '관엽식물류', midName: "아스파라거스", midId: '28', smallName: "아스파라거스", smallId: '00' },
];

// ------- 생산품 페이지


// // 상품 정보 데이터
// export const sampleProductData: Product[] = [
//   {
//     productId: 1,
//     categoryId: 100,
//     category: "과일",
//     itemId: 101,
//     item: "사과",
//     varietyId: 102,
//     variety: "홍옥",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/apple_hongok.jpg"
//   },
//   {
//     productId: 2,
//     categoryId: 100,
//     category: "과일",
//     itemId: 101,
//     item: "사과",
//     varietyId: 103,
//     variety: "후지",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/apple_fuji.jpg"
//   },
//   {
//     productId: 3,
//     categoryId: 100,
//     category: "과일",
//     itemId: 101,
//     item: "사과",
//     varietyId: 104,
//     variety: "아오리",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/apple_aori.jpg"
//   },
//   {
//     productId: 4,
//     categoryId: 200,
//     category: "채소",
//     itemId: 201,
//     item: "배추",
//     varietyId: 202,
//     variety: "월동배추",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/cabbage_winter.jpg"
//   },
//   {
//     productId: 5,
//     categoryId: 200,
//     category: "채소",
//     itemId: 201,
//     item: "배추",
//     varietyId: 203,
//     variety: "고랭지배추",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/cabbage_highland.jpg"
//   },
//   {
//     productId: 6,
//     categoryId: 300,
//     category: "채소",
//     itemId: 301,
//     item: "양파",
//     varietyId: 302,
//     variety: "양파(일반)",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/onion_normal.jpg"
//   },
//   {
//     productId: 7,
//     categoryId: 300,
//     category: "채소",
//     itemId: 301,
//     item: "양파",
//     varietyId: 303,
//     variety: "양파(수입)",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/onion_import.jpg"
//   },
//   {
//     productId: 8,
//     categoryId: 400,
//     category: "채소",
//     itemId: 401,
//     item: "상추",
//     varietyId: 402,
//     variety: "적상추",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/lettuce_red.jpg"
//   },
//   {
//     productId: 9,
//     categoryId: 500,
//     category: "채소",
//     itemId: 501,
//     item: "파프리카",
//     varietyId: 502,
//     variety: "파프리카",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/paprika.jpg"
//   },
//   {
//     productId: 10,
//     categoryId: 600,
//     category: "채소",
//     itemId: 601,
//     item: "아스파라거스",
//     varietyId: 602,
//     variety: "녹색",
//     imgUrl: "https://s3.amazonaws.com/my-bucket/products/asparagus_green.jpg"
//   }
// ];

// // 재배 정보 데이터
// export const sampleCultivationData: CultivationInfo[] = [
//   {
//     productId: 1,
//     cropName: "사과",
//     variety: "홍옥",
//     conditions: {
//       overwintering: "T", // 월동 가능
//       avgTemperatureC: "10.0",
//       minTemperatureC: "-20.0",
//       maxTemperatureC: "35.0",
//       annualRainfallMM: "1100.0",
//       sunlightHours: "2000.0",
//       drainage: "양호",
//       soilDepth: "80.0",
//       pH: "6.2"
//     },
//     cultivationFeatures:
//       "온대성 과수로서 15-24°C가 적정 생육온도이며, 겨울철 -20°C 이하에서도 내한성이 강한 품종이 있음. 개화기에는 서리 피해에 주의해야 하며, 일조량이 많을수록 착색이 우수함.",
//     managementTips:
//       "겨울 전정으로 착과 수를 조절하고, 여름철 가지 유인을 통해 통풍과 광합성을 극대화해야 함. 병충해 방지를 위해 적절한 방제 및 봉지 씌우기에 신경 써야 함."
//   },
//   {
//     productId: 4,
//     cropName: "배추",
//     variety: "월동배추",
//     conditions: {
//       overwintering: "T",
//       avgTemperatureC: "4.0",
//       minTemperatureC: "-15.0",
//       maxTemperatureC: "22.0",
//       annualRainfallMM: "900.0",
//       sunlightHours: "1600.0",
//       drainage: "양호-약간양호",
//       soilDepth: "50.0",
//       pH: "6.5"
//     },
//     cultivationFeatures:
//       "냉량성 작물로, 월동배추는 -10°C 이하에서도 생육 가능. 발아 적온은 20-25°C이며, 고온기(여름철)에는 생육이 억제될 수 있음.",
//     managementTips:
//       "충분한 시비 관리로 결구율을 높이고, 생리장해 예방 필요. 진딧물 및 노린재 방제 필수. 장마철 습해 방지를 위해 배수로 정비 철저."
//   },
//   {
//     productId: 6,
//     cropName: "양파",
//     variety: "양파(일반)",
//     conditions: {
//       overwintering: "F",
//       avgTemperatureC: "12.0",
//       minTemperatureC: "-3.0",
//       maxTemperatureC: "30.0",
//       annualRainfallMM: "1000.0",
//       sunlightHours: "1600.0",
//       drainage: "양호-약간불량",
//       soilDepth: "50.0",
//       pH: "6.2"
//     },
//     cultivationFeatures:
//       "온대 및 온난 지역에서 재배 가능하며, 발아 적온은 15-25°C. 생육 최적 온도는 17°C이며, 배수가 잘되는 토양이 필요함.",
//     managementTips:
//       "생육 초기에 충분한 관수 필요. 파밤나방, 총채벌레 방제 필수. 수확 후 0-4°C의 저온 저장 필요."
//   },
//   {
//     productId: 8,
//     cropName: "상추",
//     variety: "적상추",
//     conditions: {
//       overwintering: "F",
//       avgTemperatureC: "16.5",
//       minTemperatureC: "0.0",
//       maxTemperatureC: "28.0",
//       annualRainfallMM: "1100.0",
//       sunlightHours: "1750.0",
//       drainage: "양호-약간양호",
//       soilDepth: "50.0",
//       pH: "6.3"
//     },
//     cultivationFeatures:
//       "냉량성 작물로 15-20°C에서 생육이 가장 활발하며, 30°C 이상에서는 생육 저해 및 화아분화(꽃대 올라옴) 발생.",
//     managementTips:
//       "여름철 고온기에는 차광망을 이용한 온도 조절이 필수. 수확 전 질소 과다 시비를 피해야 질산염 농도 증가 방지 가능. 진딧물 및 노린재 방제 필요."
//   },
//   {
//     productId: 9,
//     cropName: "파프리카",
//     variety: "파프리카",
//     conditions: {
//       overwintering: "F",
//       avgTemperatureC: "19.5",
//       minTemperatureC: "5.0",
//       maxTemperatureC: "32.0",
//       annualRainfallMM: "1300.0",
//       sunlightHours: "2200.0",
//       drainage: "양호-약간양호",
//       soilDepth: "50.0",
//       pH: "6.1"
//     },
//     cultivationFeatures:
//       "재배 기간이 길며, 토양 pH 6.0-6.5에서 잘 자라고, 배수가 잘 되는 유기물 함량이 높은 토양이 적합함.",
//     managementTips:
//       "병해충(흰가루병, 탄저병) 예방을 위해 환기 및 습도 조절 필수. 적절한 줄기 유인 및 가지치기로 광합성 효율을 극대화해야 함."
//   },
//   {
//     productId: 10,
//     cropName: "아스파라거스",
//     variety: "녹색",
//     conditions: {
//       overwintering: "T",
//       avgTemperatureC: "15.5",
//       minTemperatureC: "-5.0",
//       maxTemperatureC: "30.0",
//       annualRainfallMM: "1200.0",
//       sunlightHours: "2000.0",
//       drainage: "양호-약간양호",
//       soilDepth: "50.0",
//       pH: "6.5"
//     },
//     cultivationFeatures:
//       "다년생 작물로, 첫 수확까지 2-3년이 필요하며, 최적 생육 온도는 16-20°C.",
//     managementTips:
//       "봄철 새순이 나오기 전에는 덮개 관리 및 보온이 중요. 과습에 약하므로 배수 관리를 철저히 해야 함."
//   }
// ];

// 농장 적합도
// export const sampleFarmSuitability: FarmSuitability = {
//   anayize: [
//     { reason: "월동여부", suitability: "적합" },
//     { reason: "평균 기온", suitability: "평균" },
//     { reason: "최저 기온", suitability: "부적합" },
//     { reason: "최고 기온", suitability: "적합" },
//     { reason: "연평균 강수량", suitability: "적합" },
//     { reason: "일조량", suitability: "평균" },
//     { reason: "배수등급", suitability: "적합" },
//     { reason: "유효 토심", suitability: "적합" },
//     { reason: "토양 산도", suitability: "평균" }
//   ]
// };

// export const productResponse: ProductResponseDto = {
//   product: sampleProductData[0], // 상품 정보
//   cultivationInfo: sampleCultivationData[0], // 상품 재배 정보
//   farmSuitability: sampleFarmSuitability,
// }

// 가격 샘플 데이터
export const priceResponse1: PriceResponse = {
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
    { date: "2025-02-28", variety: "홍옥", special: 5000, high: 4500, moderate: 4000, other: 3500 },
    { date: "2025-03-01", variety: "홍옥", special: 5100, high: 4550, moderate: 4050, other: 3550 },
    { date: "2025-03-02", variety: "홍옥", special: 5200, high: 4600, moderate: 4100, other: 3600 },
    { date: "2025-03-03", variety: "홍옥", special: 5150, high: 4570, moderate: 4070, other: 3570 },
    { date: "2025-03-04", variety: "홍옥", special: 5250, high: 4620, moderate: 4120, other: 3620 },
    // 아오리 데이터
    { date: "2025-02-28", variety: "아오리", special: 5300, high: 4650, moderate: 4150, other: 3650 },
    { date: "2025-03-01", variety: "아오리", special: 5350, high: 4700, moderate: 4200, other: 3700 },
    { date: "2025-03-02", variety: "아오리", special: 5400, high: 4750, moderate: 4250, other: 3750 },
    { date: "2025-03-03", variety: "아오리", special: 5450, high: 4800, moderate: 4300, other: 3800 },
    { date: "2025-03-04", variety: "아오리", special: 5500, high: 4850, moderate: 4350, other: 3850 },
    // 후지 데이터
    { date: "2025-02-28", variety: "후지", special: 6000, high: 5500, moderate: 5000, other: 4500 },
    { date: "2025-03-01", variety: "후지", special: 6050, high: 5550, moderate: 5050, other: 4550 },
    { date: "2025-03-02", variety: "후지", special: 6100, high: 5600, moderate: 5100, other: 4600 },
    { date: "2025-03-03", variety: "후지", special: 6150, high: 5650, moderate: 5150, other: 4650 },
    { date: "2025-03-04", variety: "후지", special: 6200, high: 5700, moderate: 5200, other: 4700 },
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
      {name: '홍옥' },  // 0
      {name: '아오리' }, // 1
      {name: '후지' },  // 2
       
      // 두 번째 레이어: 생산지
      {name: '경북' },  // 3
      {name: '충북' },  // 4
      {name: '경남' },  // 5
      {name: '전북' },  // 6
      {name: '전남' },  // 7

      // 세 번째 레이어: 도매시장
      {name: '안양도매시장' },    // 8
      {name: '대구북부도매' },    // 9
      {name: '포항도매시장' },    // 10
      {name: '서울가락도매시장' }, // 11
      {name: '부산도매시장' },     // 12
      {name: '대구중앙도매시장' }, // 13
      {name: '인천도매시장' },     // 14
  
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



// 사용자 정보 데이터
export const user = {
  email: "john.doe@example.com",
  pnuCode: "0987654321",
  joinDate: "2023-01-01",
};

export const farmData: FarmData = {
  overwintering: "예", // 월동 여부 (예/아니오)
  averageTemperature: "15°C", // 평균 기온
  minTemperature: "5°C", // 최저 기온
  maxTemperature: "25°C", // 최고 기온
  annualRainfall: "800mm", // 연간 강수량
  sunlightHours: "2000h", // 일조 시간

  drainage: "상", // 배수 등급 (상, 중, 하)
  soilDepth: "30cm", // 유효 토심
  slopeDegree: "5°", // 경사도
  soilTexture: "모래질", // 토성

  ph: "6.5", // 산도
  organicMatterGPerKg: "20g/kg", // 유기물
  avPMgPerKg: "15mg/kg", // 유효인산
  kmgPerKg: "30mg/kg", // 칼륨
  caMgPerKg: "40mg/kg", // 칼슘
  mgMgPerKg: "10mg/kg" // 마그네슘
};

// 추천 생산품
export const recommendedProducts = [
  { id: 1, name: "사과", reason: "추천" },
  { id: 2, name: "배", reason: "추천" },
  { id: 3, name: "딸기", reason: "추천" },
  { id: 4, name: "감자", reason: "유통량 상승" },
  { id: 5, name: "고구마", reason: "가격 상승" },
];