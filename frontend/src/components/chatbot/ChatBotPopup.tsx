import { useState, useEffect } from "react";
import { fetchWeather, fetchAlert, fetchPrice, fetchOther } from "../../api/chat_api";
import { CommonResponseDto } from "../../types/member_types";
import { ChatbotResponseDto } from "../../types/chatbot_types";
import { useNavigate } from "react-router-dom";
import { ITEM_VARIETY_MAP } from "../../pages/PricePage";

const ChatbotPopup = ({ onClose }: { onClose: () => void }) => {

  const navigate = useNavigate();

  const [screen, setScreen] = useState<string>("initial"); // 현재 활성화된 화면
  const [messages, setMessages] = useState<any[]>([]); // 챗봇 메시지 상태
  const [userInput, setUserInput] = useState<string>(""); // 사용자 입력값

  // 초기 공지 메시지 (항상 표시)
  const fixedMessages = [
    { type: "bot", content: "📢 **오늘의 공지사항**: 강풍 주의보가 발효되었습니다. 외출 시 유의하세요!" },
    { type: "bot", content: "🔥 **오늘의 인기 토픽**: '스마트 농업이 미래를 바꾼다' 기사 확인하기!" },
    { type: "bot", content: "무엇을 도와드릴까요? 아래 버튼을 클릭해주세요. 👇" },
  ];

  // 챗봇이 처음 열릴 때 공지 추가
  useEffect(() => {
    if (messages.length === 0) {
      setMessages([...fixedMessages]);
    }
  }, []);

  // 초기 화면으로 돌아가기
  const handleBackButtonClick = () => {
    setScreen("initial");
    setMessages([...fixedMessages]);
  };

  // 버튼 클릭 시 화면 변경
  const handleButtonClick = (action: string) => {
    setScreen(action);
    let initialMessage = "";

    switch (action) {
      case "alert":
        initialMessage = "🔔 알람을 받고 싶은 품목, 품종과 가격을 입력해주세요! (예: 사과 부사 2000)";
        break;
      case "weather":
        initialMessage = "🌦️ 조회할 지역의 날씨 정보를 입력해주세요. (예: 서울)";
        break;
      case "price":
        initialMessage = "💰 가격을 알고 싶은 품목, 품종과 가격을 입력해주세요! (예: 배추 고랭지배추)";
        break;
      case "other":
        initialMessage = "🌾 궁금한 농업 관련 질문을 입력해주세요 ~~ !! (예: 사과 병충해 목록)";
        break;
      default:
        initialMessage = "무엇을 도와드릴까요?";
    }

    setMessages([{ type: "bot", content: initialMessage }]);
  };

  // 사용자 입력 처리 (메뉴별 API 요청)
  const handleUserInputSubmit = async () => {
    if (!userInput.trim()) return;

    // 사용자 메시지 추가
    setMessages([...messages, { type: "user", content: userInput }]);

    let response: CommonResponseDto<ChatbotResponseDto>;

    try {
        switch (screen) {
            case "alert":
                response = await handleAlertInput()
                break;
            case "weather":
                response = await handleWeatherInput()
                break;
            case "price":
                await handlePriceInputSubmit()
                break;
            case "other":
                response = await handleOtherInput()
                break;
            default:
                throw new Error("⛔ 유효하지 않은 요청입니다.");
        }

        if (response.status == '200') {
            setMessages((prevMessages) => [
                ...prevMessages,
                { type: "user", content: response.message },
                { type: "user", content: response.data ? JSON.stringify(response.data, null, 2) : "✅ 요청이 처리되었습니다." },
            ]);
        } else {
            setMessages((prevMessages) => [
                ...prevMessages,
                { type: "user", content: `⚠️ ${response.message}` },
            ]);
        }
    } catch (error) {
        console.error("API 요청 실패:", error);
        setMessages((prevMessages) => [
            ...prevMessages,
            { type: "user", content: "⛔ 요청을 처리하는 중 오류가 발생했습니다." },
        ]);
    }

    setUserInput(""); // 입력 필드 초기화
  };

  // ✅ 알람 설정 처리
  const handleAlertInput = async () => {
    const response = await fetchAlert({ input: userInput });
    return response;
  };

  // ✅ 날씨 조회 처리
  const handleWeatherInput = async () => {
    const response = await fetchWeather({ input: userInput });
    return response;
  };

  // ✅ 기타 질문 처리
  const handleOtherInput = async () => {
    const response = await fetchOther({ input: userInput });
    return response;
  };

  // 가격 입력 처리
  const handlePriceInputSubmit = async () => {
    if (!userInput.trim()) return;
  
    setMessages((prevMessages) => [...prevMessages, { type: "user", content: userInput }]);
  
  
    try {
      // 사용자 입력을 "품목 품종" 형태로 파싱
      const [item, variety] = userInput.split(" ");
  
      // ITEM_VARIETY_MAP에서 품목과 품종 찾기
      const matchedItem = ITEM_VARIETY_MAP.find(
        (entry) => entry.item === item && entry.variety === variety
      );
  
      if (!matchedItem) {
        setMessages((prev) => [...prev, { type: "bot", content: "⚠️ 해당 품목 또는 품종 정보를 찾을 수 없습니다." }]);
        return;
      }
  
      // 서버에 가격 정보 요청 (기본 지역: 서울)
      const requestData = {
        itemCode: matchedItem.itemCode,
        varietyCode: matchedItem.varietyCode,
        region: "서울",
      };
  
      const response = await fetchPrice(requestData);
  
      if (response.status === "200") {
        // ✅ 200 응답이면 가격 페이지로 이동
        navigate("/price", { state: requestData });
      } else {
        // 오류 메시지를 챗봇에 출력
        setMessages((prev) => [
          ...prev,
          { type: "bot", content: `❌ 조회 실패: ${response.message}` },
        ]);
      }
    } catch (error) {
      console.error("API 요청 실패:", error);
      setMessages((prev) => [
        ...prev,
        { type: "bot", content: "⛔ 서버 오류로 인해 조회에 실패했습니다." },
      ]);
    }
  
    setUserInput(""); // 입력 필드 초기화
  };

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
            onKeyPress={(e) => e.key === "Enter" && handleUserInputSubmit()} 
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