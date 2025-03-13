import Header from "../components/Header";
import BestItemSlider from "../components/home/BestItemSlider";
import NewsList from "../components/home/NewsList";
import ChatBotButton from "../components/chatbot/ChatBotButton";
import LiveWeatherTopics from "../components/home/LiveWeatherTopics";
import MyLocalWeather from "../components/home/MyLocalWeather";
import { useEffect, useState } from "react";
import { getHomeInfo, getMemberInfo, pushSubscribtion } from "../api/core_api";
import { useQueryClient } from "@tanstack/react-query";
import { BestItems, HomeRequestDto, News, WeatherExpections, WeatherNotice } from "../types/home_types";
import { useAuth } from "../conntext_api/AuthProvider";
import { bestItemsFix } from "../data_sample";


// VAPID 공개 키
const VAPID_PUBLIC_KEY = import.meta.env.VAPID_PUBLIC_KEY;

const HomePage = () => {
  const [loading, setLoading] = useState(true);
  const [, setSubscription] = useState<HomeRequestDto>();
  const { login } = useAuth();


  // const navigate = useNavigate();
  const queryClient = useQueryClient();

  // Base64 → Uint8Array 변환 함수
  const urlBase64ToUint8Array = (base64String: string) => {
    const padding = "=".repeat((4 - (base64String.length % 4)) % 4);
    const base64 = (base64String + padding).replace(/\-/g, "+").replace(/_/g, "/");
    const rawData = atob(base64);
    return new Uint8Array([...rawData].map((char) => char.charCodeAt(0)));
  };

  // ArrayBuffer → Base64 변환 함수
  const arrayBufferToBase64 = (buffer: ArrayBuffer | null) => {
    if (!buffer) return "";
    return btoa(String.fromCharCode(...new Uint8Array(buffer)));
  };

  const subscribeToPushNotifications = async (): Promise<HomeRequestDto | null> => {
    if (!("serviceWorker" in navigator)) return null;
  
    try {

      // @Todo 일부 브라우저에서 워커 등록 안되는 문제 있음 
      const registration = await navigator.serviceWorker.ready;


      const permission = await Notification.requestPermission();
  
      if (permission !== "granted") {
        console.warn("⚠️ 알림 권한이 허용되지 않았습니다.");
        return null;
      }
  
      const pushSubscription = await registration.pushManager.subscribe({
        userVisibleOnly: true,
        applicationServerKey: urlBase64ToUint8Array(VAPID_PUBLIC_KEY),
      });

  
      // 🔹 키 값 확인
      const p256dhKey = pushSubscription.getKey("p256dh");
      const authKey = pushSubscription.getKey("auth");

  
      if (!p256dhKey || !authKey) {
        console.error("❌ 푸시 구독 키 정보가 올바르게 제공되지 않았습니다.");
        return null;
      }
  
      // 구독 정보를 HomeRequestDto 형식으로 변환
      const subscriptionData: HomeRequestDto = {
        device: /Mobi/i.test(navigator.userAgent) ? "mobile" : "web",
        endpoint: pushSubscription.endpoint,
        p256dh: arrayBufferToBase64(p256dhKey),
        auth: arrayBufferToBase64(authKey),
      };

      await pushSubscribtion(subscriptionData);
  
      // console.log("✅ 최종 Push Subscription Data:", subscriptionData);
  
      setSubscription(subscriptionData);

      return subscriptionData;

    } catch (error) {
      // console.error("❌ 푸시 구독 실패:", error);
      return null;
    }
  };

  useEffect(() => {
  const fetchUserData = async () => {
    try {
      await subscribeToPushNotifications();

      const response = await getHomeInfo();

      queryClient.setQueryData(["products"], bestItemsFix);
      queryClient.setQueryData(["weatherNotice"], response.data.weatherNotice);
      queryClient.setQueryData(["weatherExpect"], response.data.weatherExpect);
      queryClient.setQueryData(["news"], response.data.news);

      const memberResponse = await getMemberInfo();

      if (memberResponse.status === "200") {
        sessionStorage.setItem("bomnet_user", memberResponse.data.memberId);
        if (memberResponse.data.pnu) {
          sessionStorage.setItem("bomnet_pnu", memberResponse.data.pnu);
        }
        login({ 
          memberId: memberResponse.data.memberId, 
          pnu: memberResponse.data.pnu || "" 
        });
      } else {
        console.log("미가입 사용자");
      }
    } catch (error) {
      console.error("Error fetching home info:", error);
    } finally {
      setLoading(false);
    }
  };

  fetchUserData();
}, []);

  const productsData = queryClient.getQueryData<BestItems>(["products"]);
  const weatherNoticeData = queryClient.getQueryData<WeatherNotice[]>(["weatherNotice"]);
  const weatherExpectData = queryClient.getQueryData<WeatherExpections>(["weatherExpect"]);
  const newsData = queryClient.getQueryData<News[]>(["news"]);

  // 로딩 중일 때는 로딩 UI 표시
  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <div className="spinner"></div>
      </div>
    );
  }
  
  // 로딩이 완료되면 홈 페이지 콘텐츠 렌더링
  return (
    <div className="font-sans">
      <Header />
      <main className="p-4">
        <h1 className="text-2xl font-bold text-center mb-6">
          농산물 종합정보 시스템 "봄넷"
        </h1>
        <BestItemSlider productsData={productsData} />
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-8 max-w-4xl mx-auto">
          <LiveWeatherTopics weatherNoticeData={weatherNoticeData} />
          <MyLocalWeather weatherExpectData={weatherExpectData} />
        </div>
        <NewsList newsData={newsData} />
      </main>
      <ChatBotButton />
    </div>
  );
};

export default HomePage;
