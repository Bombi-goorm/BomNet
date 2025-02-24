import { WeatherInfo } from "./home_types";

// 챗봇 요청
export interface ChatbotRequestDto {
    input?: string;
    bigId?: string;
    midId?: string;   
    smallId?: string;  
    region?: string;
    price?: number;    
}


export interface ChatbotResponseDto {
    crop?: string;
    price?: number;
    answer?: string;
    location?: string;
    weatherInfo?: WeatherInfo;
    intent?: string; 
    response_data?: {
        content?: string;
    };
}
