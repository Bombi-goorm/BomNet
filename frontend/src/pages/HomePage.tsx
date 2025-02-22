import Header from "../components/Header";
import BestItemSlider from "../components/home/BestItemSlider";
import NewsList from "../components/home/NewsList";
import ChatBotButton from "../components/chatbot/ChatBotButton";
import LiveWeatherTopics from "../components/home/LiveWeatherTopics";
import MyLocalWeather from "../components/home/MyLocalWeather";
import { useEffect, useState } from "react";
import { homeInfo, memberInfo } from "../api/core_api";
import { useQueryClient } from "@tanstack/react-query";

const HomePage = () => {
  const [loading, setLoading] = useState(true);

  // const navigate = useNavigate();
  const queryClient = useQueryClient();

  useEffect(() => {
    const savedUser = sessionStorage.getItem("bomnet_user");

    // 세션스토리지에 사용자 정보가 있으면 바로 로딩 완료
    if (savedUser) {
      setLoading(false);
      return;
    }
    const fetchUserData = async () => {
      
      try {
        // homeInfo API 호출 
        const response = await homeInfo(); 

        // if(response.status === '500'){
        //   navigate('/500')
        // }

        // 정보 캐싱 
        // queryClient.setQueryData(["products"], response.data.bestIetms);
        // queryClient.setQueryData(["weatherNotice"], response.data.weatherNotice);
        // queryClient.setQueryData(["weatherExpect"], response.data.weatherExpect);
        // queryClient.setQueryData(["news"], response.data.news);

        queryClient.setQueryData(["products"], response.bestItems);
        queryClient.setQueryData(["weatherNotice"], response.weatherNotice);
        queryClient.setQueryData(["weatherExpect"], response.weatherExpect);
        queryClient.setQueryData(["news"], response.news);

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
