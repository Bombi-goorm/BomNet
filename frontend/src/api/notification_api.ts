import axios from "axios";
import { CommonResponseDto } from "../types/member_types";

// Axios 인스턴스 생성
const api = axios.create({
 // baseURL: import.meta.env.VITE_NOTI_HOST, // 백엔드 주소
    baseURL: 'http://localhost:8184', // 로컬 테스트
    withCredentials: true, // HttpOnly 쿠키를 위한 설정
    headers: {
      'Content-Type': import.meta.env.CONTENT_TYPE,
    },
});


// 알림 등록
export const setNotification = async (): Promise<CommonResponseDto<null>> => {
    const response = await api.post<CommonResponseDto<null>>(`/notification`);
    return response.data;
};