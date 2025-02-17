// 공통 응답 타입
export interface CommonResponseDto<T> {
    status: string, // 응답 코드 
    message: string, // 응답 메세지
    data: T, // DTO 객체
}

// 회원가입 요청
export interface SignupRequestDto {
    BJD?: string,
    PNU?: string,
}

// 사용자 정보 응답 
export interface InfoResponseDto {
    memberId: string,
    email: string,
    BJD?: string,
    PNU?: string,
}

// 홈화면 정보 응답
export interface HomeDto {
    bestItems: BestItems,
    weatherNotice: WeatherNotice[],
    weatherExpect: WeatherExpections,
    news: News[],
}

// 인기/관심 상품
export interface BestItems {
    products: Product[]
}

// 상품 객체
export interface Product {
    productId: number,
    productName: string,
    imgUrl: string,
    dayPrice: ProductPrice[],
}

// 상품 가격 추이
export interface ProductPrice {
    date: string,
    price: string,
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


// 챗봇 요청
export interface ChatbotRequestDto {
    type: string,
    input: string,
}
