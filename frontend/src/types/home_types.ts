// 홈화면 정보 응답
export interface HomeDto {
    bestItems: BestItems,
    weatherNotice: WeatherNotice[],
    weatherExpect: WeatherExpections,
    news: News[],
}

// 인기/관심 상품
export interface BestItems {
    products: HomeProduct[]
}

// 상품 객체
export interface HomeProduct {
    productId: number,
    productName: string,
    imgUrl: string,
    dayPrice: ProductPrice[],
}

// 상품 가격 추이
export interface ProductPrice {
    date: string,
    price: number,
}

// 기상 특보
export interface WeatherNotice {
    dateTime: string,
    location: string,
    content: string,
}

// 기상 예보
export interface WeatherExpections {
    location: string,
    weatherInfo: WeatherInfo[]
}

// 날씨 정보
export interface WeatherInfo {
    weather: string,
    temperature: string,
    humidity: string,
    wind: string,
    dateTime: string,
}

// 뉴스 
export interface News {
    title: string,
    content: string,
    dateTime: string,
    newsLink: string,
}