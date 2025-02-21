// 챗봇 요청
export interface ChatbotRequestDto {
    input?: string,
    midId?: string;
    smallId?: string;
    region?: string;
}

export interface ChatbotResponseDto {
    crop?: string,
    price?: number,
    answer?: string,
    location?: string,
    temperature?: string,
    humidity?: string,
    wind_speed?: string,
    weather_condition?: string,
}
