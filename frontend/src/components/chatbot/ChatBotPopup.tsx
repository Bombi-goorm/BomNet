import { useState, useEffect } from "react";
import { fetchWeather, fetchAlert, fetchPrice, fetchOther } from "../../api/chat_api";
import { CommonResponseDto } from "../../types/member_types";
import { ChatbotRequestDto, ChatbotResponseDto } from "../../types/chatbot_types";
import { useNavigate } from "react-router-dom";
import { WeatherInfo } from "../../types/home_types";
import { useAuth } from "../../conntext_api/AuthProvider";



const ChatbotPopup = ({ onClose }: { onClose: () => void }) => {
  const storedMemberId = sessionStorage.getItem("bomnet_user") ?? undefined; 

  const [memberId, setMemberId] = useState<string | undefined>(storedMemberId || undefined);

  useEffect(() => {
    const storedMemberId = sessionStorage.getItem("bomnet_user");
    if (storedMemberId) {
      setMemberId(storedMemberId);
    } else {
      setMemberId(undefined);
    }
  }, []);

  const { isAuthenticated } = useAuth();

  const navigate = useNavigate();

  const [screen, setScreen] = useState<string>("initial"); // 현재 활성화된 화면
  const [messages, setMessages] = useState<any[]>([]); // 챗봇 메시지 상태
  const [userInput, setUserInput] = useState<string>(""); // 사용자 입력값

  // 초기 공지 메시지 (항상 표시)
  let fixedMessages: { type: string; content: string; }[] = [];
  
  if(isAuthenticated) {
    fixedMessages = [
      { type: "bot", content: "📢 오늘의 공지사항: 강풍 주의보가 발효되었습니다. 외출 시 유의하세요!" },
      { type: "bot", content: "🔥 오늘의 인기 토픽: '스마트 농업이 미래를 바꾼다'" },
      { type: "bot", content: "무엇을 도와드릴까요? 아래 버튼을 클릭해주세요. 👇" },
    ];
  }else{
    fixedMessages = [
      { type: "bot", content: "📢 오늘의 공지사항: 강풍 주의보가 발효되었습니다. 외출 시 유의하세요!" },
      { type: "bot", content: "🔥 오늘의 인기 토픽: '스마트 농업이 미래를 바꾼다'" },
    ]
  }
 
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
    if (!isAuthenticated) {
      setMessages([{ type: "bot", content: "🔒 이 기능은 로그인 후 이용 가능합니다." }]);
      return;
    }
    setScreen(action);
    let initialMessage = "";

    switch (action) {
      case "alert":
        initialMessage = "🔔 알람을 받고 싶은 품종과 가격, 지역을 입력해주세요!";
        break;
      case "weather":
        initialMessage = "🌦️ 조회할 지역의 날씨 정보를 입력해주세요! (예: 서울)";
        break;
      case "price":
        initialMessage = "💰 가격을 알고 싶은 품목을 입력해주세요! (예: 배추)";
        break;
      case "other":
        initialMessage = "🌾 궁금한 농업 관련 질문을 입력해주세요 ~~ !! (예: 사과 병충해 목록)";
        break;
      default:
        initialMessage = "무엇을 도와드릴까요?";
    }

    setMessages([{ type: "bot", content: initialMessage }]);
  };

  //  사용자 입력 처리 (API 요청)
  const handleUserInputSubmit = async () => {
    
    if (!userInput.trim()) return;

    setMessages([...messages, { type: "user", content: userInput }]);

    try {
      let response: CommonResponseDto<ChatbotResponseDto> | undefined;
      console.log(response?.status)
      
        switch (screen) {
          case "alert":
            await handleAlertInput();
            break;
          case "weather":
            response = await handleWeatherInput();
            break;
          case "price":
            response = await handlePriceInputSubmit();
            break;
          case "other":
            response = await handleOtherInput();
            break;
          default:
            throw new Error("⛔ 유효하지 않은 요청입니다.");
        }
     
    } catch (error) {
      setMessages((prev) => [...prev, { type: "bot", content: "⛔ 요청 처리 중 오류가 발생했습니다." }]);
    }

    setUserInput(""); // 입력 필드 초기화
  };

  //  알람 설정 처리
  const handleAlertInput = async () => {
    if (!userInput.trim()) return;
  
    try {
      // 사용자 입력을 그대로 백엔드에 전달하여 LLM이 정보를 추출하도록 요청
      const response: CommonResponseDto<ChatbotResponseDto> = await fetchAlert({
        input: userInput,
        memberId: memberId,
      });
  
      // 응답 처리
      if (response.status === "200") {
          setMessages((prev) => [
            ...prev,
            {
              type: "bot",
              content: response.message,
            },
          ]);        
      } else if(response.status === "400"){
        setMessages((prev) => [
          ...prev,
          {
            type: "bot",
            content: response.message,
          },
        ]);
      } else if(response.status === "404"){
        setMessages((prev) => [
          ...prev,
          {
            type: "bot",
            content: response.message,
          },
        ]);
      }
    } catch (error) {
      setMessages((prev) => [
        ...prev,
        { type: "bot", content: "⛔ 서버 오류로 인해 알림 등록에 실패했습니다." },
      ]);
    }
  };

  //  날씨 조회 처리
  const handleWeatherInput = async () => {
    if (!userInput.trim()) return;

    try {
      const response: CommonResponseDto<ChatbotResponseDto> = await fetchWeather({
        input: userInput,
        memberId: memberId
      });

      if (response.status === "200" && response.data) {
        const { location, weatherInfo } = response.data;

        if (!weatherInfo) {
          setMessages((prev) => [
            ...prev,
            { type: "bot", content: `⚠️ '${userInput}' 지역의 날씨 정보를 찾을 수 없습니다.` },
          ]);
          return;
        }

        const formattedWeather = formatWeatherMessage(location ?? '서울', weatherInfo);
        
        setMessages((prev) => [
          ...prev,
          { type: "bot", content: formattedWeather },
        ]);
        return response;
      } else {
        setMessages((prev) => [
          ...prev,
          { type: "bot", content: `❌ 날씨 조회 실패: ${response.message || "알 수 없는 오류 발생"}` },
        ]);
        return response;
      }
    } catch (error) {
      setMessages((prev) => [
        ...prev,
        { type: "bot", content: "⛔ 서버 오류로 인해 날씨 정보를 가져오지 못했습니다." },
      ]);
      
    }
  };

  const getWeatherEmoji = (sky: string): string => {
    if (!sky) return "❓";
  
    const lower = sky.toLowerCase();
  
    if (lower.includes("맑음") || lower.includes("clear")) return "☀️";
    if (lower.includes("흐림") || lower.includes("cloud")) return "☁️";
    if (lower.includes("구름") || lower.includes("partly")) return "⛅";
    if (lower.includes("비") || lower.includes("rain")) return "🌧️";
    if (lower.includes("눈") || lower.includes("snow")) return "❄️";
    if (lower.includes("소나기") || lower.includes("shower")) return "🌦️";
    if (lower.includes("안개") || lower.includes("fog")) return "🌫️";
  
    return "🌈"; // 기본값
  };
  
  
  
  // 날씨 데이터 포맷팅
  const formatWeatherMessage = (location: string, weatherInfo: WeatherInfo): string => {
    const weatherEmoji = getWeatherEmoji(weatherInfo.weather.sky);
    return `📍 ${location} 지역 날씨 정보\n\n`
      + `📅 시간: ${new Date(weatherInfo.forecastTime ?? new Date().toISOString()).toLocaleTimeString("ko-KR", {
        hour: "2-digit",
        minute: "2-digit",
        hour12: true,
      })}\n`
      + `${weatherEmoji} 날씨: ${weatherInfo.weather.sky}\n`
      + `🌡️ 온도: ${weatherInfo.temperature}°C\n`
      + `💧 습도: ${weatherInfo.humidity}%\n`
      + `🌬️ 바람: ${weatherInfo.windSpeed}km/s`;
  };
  
  //  가격 조회 요청 -- 수정필요 ( 품목 전달 )
  const handlePriceInputSubmit = async () => {
    if (!userInput.trim()) return;

    try {
      // // ✅ 품목 이름을 midName 기준으로 검색
      // const matchedItems = ITEM_VARIETY_MAP.filter((entry) => entry.midName === userInput);

      // if (matchedItems.length === 0) {
      //   setMessages((prev) => [...prev, { type: "bot", content: "⚠️ 해당 품목 정보를 찾을 수 없습니다." }]);
      //   return;
      // }

      // // 품종 중 하나를 랜덤 선택
      // const randomVariety = matchedItems[Math.floor(Math.random() * matchedItems.length)];

      // setMessages((prev) => [
      //   ...prev,
      //   { type: "bot", content: `🔍 ${userInput}의 품종 중 '${randomVariety.smallName}'를 선택하였습니다.` },
      // ]);

      // ✅ API 요청 데이터 (input)
      const requestData: ChatbotRequestDto = {
        memberId: memberId,
        input: userInput,
      };

      const response = await fetchPrice(requestData);


      if (response.status === "200") {
        navigate("/price", { state: response.data });
        return response;
      } else {
        setMessages((prev) => [...prev, { type: "bot", content: `❌ 조회 실패: ${response.message}` }]);
        return response;
      }
    } catch (error) {
      setMessages((prev) => [...prev, { type: "bot", content: "⛔ 서버 오류로 인해 조회에 실패했습니다." }]);
    }
  };

  //  기타 질문 처리 ( 병충해 등 )
  const handleOtherInput = async () => {
    try {
      const response = await fetchOther({ memberId: memberId, input: userInput });
  
      if (!response || !response.data) {
        setMessages((prev) => [...prev, { type: "bot", content: "⛔ 응답을 가져올 수 없습니다." }]);
        return;
      }
  
      //  GPT 응답을 사용자 채팅에 추가
      handleBotResponse(response.data);
  
      return response;
    } catch (error) {
      setMessages((prev) => [...prev, { type: "bot", content: "⛔ 서버 오류로 인해 요청을 처리할 수 없습니다." }]);
    }
  };

  //  GPT 응답을 사용자에게 보기 좋게 출력
  const handleBotResponse = (data: ChatbotResponseDto) => {
    let formattedContent = "";


    //  'intent'가 있을 경우, 보기 좋게 변환
    // const intentText = data.intent?.replace(/[_']/g, " ").trim() || "정보";

    // formattedContent = `📌 **${intentText}**\n\n`;

    //  `response_data` 확인 후 출력
    if (data.response_data?.content) {
      formattedContent += data.response_data.content;
    } else {
      formattedContent += "📎 응답을 가져올 수 없습니다.";
    }

    setMessages((prev) => [...prev, { type: "bot", content: formattedContent }]);
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
            style={{ whiteSpace: "pre-line" }}
          >
            {msg.content}
          </div>
        ))}
      </div>

      {/* 초기 화면 버튼들 (로그인 필요 여부 확인) */}
      {screen === "initial" && (
        <div className="grid grid-cols-2 gap-3 mt-4">
          <button
            onClick={() => handleButtonClick("alert")}
            className={`py-2 rounded-lg ${isAuthenticated ? "bg-red-500 hover:bg-red-600 text-white" : "bg-gray-300 text-gray-500 cursor-not-allowed"}`}
          >
            알람 설정
          </button>
          <button
            onClick={() => handleButtonClick("weather")}
            className={`py-2 rounded-lg ${isAuthenticated ? "bg-blue-500 hover:bg-blue-600 text-white" : "bg-gray-300 text-gray-500 cursor-not-allowed"}`}
          >
            날씨 정보
          </button>
          <button
            onClick={() => handleButtonClick("price")}
            className={`py-2 rounded-lg ${isAuthenticated ? "bg-yellow-500 hover:bg-yellow-600 text-white" : "bg-gray-300 text-gray-500 cursor-not-allowed"}`}
          >
            가격 정보
          </button>
          <button
            onClick={() => handleButtonClick("other")}
            className={`py-2 rounded-lg ${isAuthenticated ? "bg-green-500 hover:bg-green-600 text-white" : "bg-gray-300 text-gray-500 cursor-not-allowed"}`}
          >
            기타 질문
          </button>
        </div>
      )}

           {/* 로그인 유도 메시지 */}
           {!isAuthenticated && (
        <div className="mt-4 flex flex-col items-center text-center">
          <p className="text-gray-700">🔒 로그인이 필요합니다.</p>
          <button
            onClick={() => navigate("/login")}
            className="mt-2 bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-lg"
          >
            로그인 페이지로 이동
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