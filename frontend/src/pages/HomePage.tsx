import Header from "../components/Header";
import BestItemSlider from "../components/home/BestItemSlider";
import NewsList from "../components/home/NewsList";
import ChatBotButton from "../components/chatbot/ChatBotButton";
import LiveWeatherTopics from "../components/home/LiveWeatherTopics";
import MyLocalWeather from "../components/home/MyLocalWeather";
import { useEffect, useState } from "react";
import { homeInfo, memberInfo } from "../api/core_api";
import { useQueryClient } from "@tanstack/react-query";
import { HomeRequestDto } from "../types/home_types";

// VAPID 공개 키
const VAPID_PUBLIC_KEY =
  "BNCG1iL82tnaqBApiVjuIiP38AoFMbeVLLzlogIG3PM3bcfeRA6CtMs009-Z3Qvy_MIKZdYipQ-L8KpBWR092i4";

const HomePage = () => {
  const [loading, setLoading] = useState(true);
  const [, setSubscription] = useState<HomeRequestDto>();

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
  
      console.log("✅ Push Subscription:", pushSubscription);
  
      // 🔹 키 값 확인
      const p256dhKey = pushSubscription.getKey("p256dh");
      const authKey = pushSubscription.getKey("auth");
  
      console.log("🔍 p256dh Key:", p256dhKey);
      console.log("🔍 auth Key:", authKey);
  
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
  
      console.log("✅ 최종 Push Subscription Data:", subscriptionData);
  
      setSubscription(subscriptionData);
      return subscriptionData;
    } catch (error) {
      console.error("❌ 푸시 구독 실패:", error);
      return null;
    }
  };

  useEffect(() => {
    const savedUser = sessionStorage.getItem("bomnet_user");

    // 세션스토리지에 사용자 정보가 있으면 바로 로딩 완료
    if (savedUser) {
      setLoading(false);
      return;
    }
    const fetchUserData = async () => {

      try {
        const subscriptionData = await subscribeToPushNotifications();

        if (!subscriptionData) {
          console.warn("❌ 푸시 구독 정보가 없습니다. API 호출을 중단합니다.");
          return;
        }
     
        // homeInfo API 호출 
        const response = await homeInfo(subscriptionData); 

        // if(response.status === '500'){
        //   navigate('/500')
        // }

        // 정보 캐싱 
        queryClient.setQueryData(["products"], response.data.bestItems);
        queryClient.setQueryData(["weatherNotice"], response.data.weatherNotice);
        queryClient.setQueryData(["weatherExpect"], response.data.weatherExpect);
        queryClient.setQueryData(["news"], response.data.news);

        // queryClient.setQueryData(["products"], response.bestItems);
        // queryClient.setQueryData(["weatherNotice"], response.weatherNotice);
        // queryClient.setQueryData(["weatherExpect"], response.weatherExpect);
        // queryClient.setQueryData(["news"], response.news);

        // 추가로 사용자 정보를 가져오기 위한 API 호출
        const memberResponse = await memberInfo();
        // API 응답이 성공적이면 세션스토리지에 저장
        if (memberResponse.status === '200') {
          sessionStorage.setItem("bomnet_user", JSON.stringify(memberResponse.data.memberId));
          sessionStorage.setItem("bomnet_pnu", JSON.stringify(memberResponse.data.PNU));
        }
      } catch (error) {
        console.error("Error fetching home info:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

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
          농업 생산 증대를 위한 농산물 종합정보 시스템 "봄넷"
        </h1>
        <BestItemSlider />
        {/* 기상 토픽과 내 지역 날씨 */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-8 max-w-4xl mx-auto">
          <LiveWeatherTopics />
          <MyLocalWeather />
        </div>
        <NewsList />
      </main>
      <ChatBotButton />
    </div>
  );
};

export default HomePage;
