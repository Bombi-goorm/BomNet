import Header from "../components/Header";
import BestItemSlider from "../components/home/BestItemSlider";
import NewsList from "../components/home/NewsList";
import ChatBotButton from "../components/chatbot/ChatBotButton";
import LiveWeatherTopics from "../components/home/LiveWeatherTopics";
import MyLocalWeather from "../components/home/MyLocalWeather";

const HomePage = () => {
  return (
    <div className="font-sans">
      <Header />
      <main className="p-4">
        <h1 className="text-2xl font-bold text-center mb-6">
          농업 생산 증대를 위한 농산물 유통 종합정보 시스템 "봄넷"
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
