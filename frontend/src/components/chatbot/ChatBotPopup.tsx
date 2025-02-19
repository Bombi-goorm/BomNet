import { useState, useEffect } from "react";
import axios from "axios";

const ChatbotPopup = ({ onClose }: { onClose: () => void }) => {
  const [screen, setScreen] = useState<string>("initial");
  const [messages, setMessages] = useState<any[]>([]);
  const [userInput, setUserInput] = useState<string>("");


  // ✅ 공지 및 오늘의 토픽 (초기화 시 항상 고정)
  const fixedMessages = [
    { type: "bot", content: "📢 **오늘의 공지사항**: 강풍 주의보가 발효되었습니다. 외출 시 유의하세요!" },
    { type: "bot", content: "🔥 **오늘의 인기 토픽**: '스마트 농업이 미래를 바꾼다' 기사 확인하기!" },
    { type: "bot", content: "무엇을 도와드릴까요? 아래 버튼을 클릭해주세요. 👇" },
  ];

  useEffect(() => {
    // 챗봇이 처음 열릴 때 공지 추가
    if (messages.length === 0) {
      setMessages([...fixedMessages]);
    }
  }, []);


  const handleBackButtonClick = () => {
    setScreen("initial");
    setMessages([...fixedMessages]); 
  };

  const handleButtonClick = (action: string) => {
    if (action === "alert") {
      setScreen("alert");
      setMessages([{ type: "bot", content: "알람을 받고 싶은 품목과 가격을 알려주세요!" }]);
    } else if (action === "weather") {
      setScreen("weather");
      setMessages([{ type: "bot", content: "사용자 지역의 날씨는 다음과 같습니다." }]);
    } else if (action === "price") {
      setScreen("price");
      setMessages([{ type: "bot", content: "어떤 품종의 가격이 궁금하신가요?" }]);
    } else {
      setScreen("other");
      setMessages([{ type: "bot", content: "농업 관련 질문을 해주세요!" }]);
    }
  };


  const handleUserInputSubmit = () => {
    if (!userInput.trim()) return;

    setMessages([...messages, { type: "user", content: userInput }]);
    setUserInput("");
  };

  const fetchWeather = async () => {
    try {
      const response = await axios.post("http://localhost:8000/gpt/weather");
      const weatherMessage = response.data.weather || "날씨 정보를 가져올 수 없습니다.";
      setMessages((prevMessages) => [...prevMessages, { type: "bot", content: `날씨 정보: ${weatherMessage}` }]);
    } catch (error) {
      setMessages((prevMessages) => [...prevMessages, { type: "bot", content: "날씨 정보를 가져오는 중 오류가 발생했습니다." }]);
    }
  };

  useEffect(() => {
    if (screen === "weather") {
      fetchWeather();
    }
  }, [screen]);

  return (
    <div className="fixed bottom-20 right-6 w-96 h-[520px] bg-gradient-to-r from-green-50 to-green-100 shadow-xl rounded-2xl p-4 flex flex-col">
      {/* 헤더 */}
      <div className="flex items-center justify-between mb-4">
        {screen !== "initial" && (
          <button
            onClick={handleBackButtonClick}
            className="bg-gray-300 text-gray-700 w-8 h-8 rounded-full flex items-center justify-center shadow"
          >
            ←
          </button>
        )}
        <p className="text-lg font-bold flex-grow text-center">🌱 봄넷 챗봇</p>
        <button onClick={onClose} className="text-gray-500 hover:text-gray-700 text-xl">
          ✕
        </button>
      </div>

      {/* 대화 내용 */}
      <div className="flex-1 overflow-y-auto space-y-3 p-2 bg-white rounded-lg shadow-inner">
        {messages.map((msg, index) => (
          <div
            key={index}
            className={`p-3 rounded-lg w-fit max-w-xs ${
              msg.type === "user" ? "bg-green-500 text-white self-end ml-auto" : "bg-gray-200 text-gray-800 self-start"
            }`}
          >
            {msg.content}
          </div>
        ))}
      </div>

      {/* 초기 화면 버튼들 */}
      {screen === "initial" && (
        <div className="grid grid-cols-2 gap-3 mt-4">
          <button onClick={() => handleButtonClick("alert")} className="bg-red-500 hover:bg-red-600 text-white py-2 rounded-lg">
            알람 설정
          </button>
          <button onClick={() => handleButtonClick("weather")} className="bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg">
            날씨 정보
          </button>
          <button onClick={() => handleButtonClick("price")} className="bg-yellow-500 hover:bg-yellow-600 text-white py-2 rounded-lg">
            가격 정보
          </button>
          <button onClick={() => handleButtonClick("other")} className="bg-green-500 hover:bg-green-600 text-white py-2 rounded-lg">
            기타 질문
          </button>
        </div>
      )}

      {/* 사용자 입력창 */}
      {screen !== "initial" && (
        <div className="mt-3 flex">
          <input
            type="text"
            className="flex-1 p-2 border border-gray-300 rounded-l-lg focus:ring focus:ring-green-300 outline-none"
            value={userInput}
            onChange={(e) => setUserInput(e.target.value)}
            placeholder="메시지를 입력하세요..."
          />
          <button onClick={handleUserInputSubmit} className="bg-green-500 hover:bg-green-600 text-white px-4 rounded-r-lg">
            전송
          </button>
        </div>
      )}
    </div>
  );
};

export default ChatbotPopup;
