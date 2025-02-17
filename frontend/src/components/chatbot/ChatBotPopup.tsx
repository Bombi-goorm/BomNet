import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ChatbotPopup = ({ onClose }: { onClose: () => void }) => {
  const [screen, setScreen] = useState<string>("initial");
  const [messages, setMessages] = useState<any[]>([]);
  const [userInput, setUserInput] = useState<string>("");

  // Handle button click actions
  const handleButtonClick = (action: string) => {
    if (action === "버튼 1") {
      setScreen("alert");
      setMessages([
        {
          type: "bot",
          content: "알람을 받고 싶은 품목과 가격을 알려주세요 !",
        },
      ]);
    } else if (action === "버튼 2") {
      setScreen("weather");
      setMessages([
        {
          type: "bot",
          content: "사용자 지역의 날씨는 다음과 같습니다",
        },
      ]);
    } else if (action === "버튼 3") {
      setScreen("price");
      setMessages([
        {
          type: "bot",
          content: "어떤 품종의 가격이 궁금하신가요?",
        },
      ]);
    } else {
      setMessages([...messages, { type: "bot", content: "농업과 관련된 정보를 물어보세요 !" }]);
      setScreen("other");
    }
  };

  // Back button click to go back to the initial screen
  const handleBackButtonClick = () => {
    setScreen("initial");
    setMessages([]);
  };

  // Handle user input and submit
  const handleUserInputSubmit = async () => {
    if (!userInput.trim()) return;
  
    setMessages([...messages, { type: "user", content: userInput }]);
    setUserInput("");
    
    // Function to handle alert API request
    const handleAlertRequest = async () => {
      const apiUrl = "http://localhost:8000/gpt/alarm";
      const requestBody = { user_input: userInput };
  
      try {
        const response = await fetch(apiUrl, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(requestBody),
        });
        const result = response.ok ? await response.json() : { message: "API 요청에 실패했습니다. 다시 시도해 주세요." };

        // 데이터가 올바르게 왔을 경우 가공하여 표시
        console.log(response);
        let botMessage = "";
        if (result.Item) {
          if (result.Item) botMessage += '품목: ${result.Item}';
          botMessage += '\n품종: ${result.Variety}';
          if (result.Price) botMessage += `\n가격: ${result.Price}원`;
          botMessage += '으로 알람 설정하였습니다! \n 다른 알람이 필요하신가요?'
        }

        setMessages((prevMessages) => [...prevMessages, { type: "bot", content: botMessage }]);
      } catch (error) {
        console.error("Error during API request:", error);
        setMessages((prevMessages) => [...prevMessages, { type: "bot", content: "서버와의 연결에 문제가 있습니다. 다시 시도해 주세요." }]);
      }
    };
  
    // Function to handle weather API request
    // const handleWeatherRequest = async () => {
    //   const apiUrl = "http://127.0.0.1:8000/gpt/weather";
    //   const requestBody = { user_input: userInput };
  
    //   try {
    //     const response = await fetch(apiUrl, {
    //       method: "POST",
    //       headers: { "Content-Type": "application/json" },
    //       body: JSON.stringify(requestBody),
    //     });
    //     if (response.ok) {
    //       const result = await response.json();
    //       setMessages((prevMessages) => [
    //         ...prevMessages,
    //         { type: "bot", content: result.message || "응답을 받을 수 없습니다." },
    //       ]);
    //     } else {
    //       setMessages((prevMessages) => [
    //         ...prevMessages,
    //         { type: "bot", content: "API 요청에 실패했습니다. 다시 시도해 주세요." },
    //       ]);
    //     }
    //   } catch (error) {
    //     console.error("Error during API request:", error);
    //     setMessages((prevMessages) => [
    //       ...prevMessages,
    //       { type: "bot", content: "서버와의 연결에 문제가 있습니다. 다시 시도해 주세요." },
    //     ]);
    //   }
    // };
  
    // Function to handle price API request
    const handlePriceRequest = async () => {
      const apiUrl = "http://localhost:8000/gpt/price";
      const requestBody = { user_input: userInput };
  
      try {
        const response = await fetch(apiUrl, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*", // CORS 설정 추가
          },
          body: JSON.stringify(requestBody),
        });
    
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
    
        const result = await response.json();
    
        // 응답 데이터 처리
        let botMessage = "";
        if (result.Item || result.Variety) {
          if (result.Item) botMessage += `품목: ${result.Item}\n`;
          if (result.Variety) botMessage += `품종: ${result.Variety}의 가격은 ~~~~!`;
          botMessage += '다른 정보가 필요하신가요?';
        } else {
          botMessage = "어떤 품종의 가격이 궁금한지 다시 입력해 주세요.";
        }
    
        setMessages((prevMessages) => [...prevMessages, { type: "bot", content: botMessage }]);
      } catch (error) {
        console.error("Error during API request:", error);
        setMessages((prevMessages) => [
          ...prevMessages,
          { type: "bot", content: "서버와의 연결에 문제가 있습니다. 다시 시도해 주세요." }
        ]);
      }
    };
  
    // Function to handle other API request
    const handleOtherRequest = async () => {
      const apiUrl = "http://localhost:8000/gpt/other";
      const requestBody = { user_input: userInput };
  
      try {
        const response = await fetch(apiUrl, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*", // CORS 설정 추가
          },
          body: JSON.stringify(requestBody),
        });
        console.log(response);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
    
        const result = await response.json();
    
        // 응답 처리
        let botMessage = "";
        if (result.status === "success") {
          const answer = result.answer;
          const points = answer.split(/\d+\./).filter((item: string) => item.trim());
          points.forEach((point: string, index: number) => {
            botMessage += `${index + 1}️⃣ ${point.trim()}\n\n`;
          });
          botMessage += "추가 질문이 있으시다면 언제든 물어보세요! 🌟";
        } else {
          botMessage = result.message || "응답을 받을 수 없습니다.";
        }
    
        setMessages((prevMessages) => [...prevMessages, { type: "bot", content: botMessage }]);
      } catch (error) {
        console.error("Error during API request:", error);
        setMessages((prevMessages) => [
          ...prevMessages,
          { type: "bot", content: "서버와의 연결에 문제가 있습니다. 다시 시도해 주세요." }
        ]);
      }
    };
  
    // Determine which API function to call based on the current screen
    if (screen === "alert") {
      handleAlertRequest();
    } else if (screen === "price") {
      handlePriceRequest();
    } else {
      handleOtherRequest();
    }
  };
  
  const fetchWeather = async () => {
    try {
      const response = await axios.post('http://localhost:8000/gpt/weather');
      const weatherMessage = response.data.weather || 'Unable to fetch weather information.';
      console.log(response);
      setMessages((prevMessages) => [
        ...prevMessages,
        { type: 'system', content: `Weather Info: ${weatherMessage}` },
      ]);
    } catch (error) {
      setMessages((prevMessages) => [
        ...prevMessages,
        { type: 'system', content: 'An error occurred while fetching weather data.' },
      ]);
    } 
  };

  useEffect(() => {
    if (screen === "weather") {
      fetchWeather();
    }
  }, [screen]);

  
  const messageContainerStyle = "h-[calc(100%-80px)] overflow-y-auto mb-16 pb-4";

  const renderContent = () => {
    if (screen === "initial") {
      return (
        <div>
          <div className="p-2 bg-gray-100 rounded-lg mb-2">
            안녕하세요! 무엇을 도와드릴까요?
          </div>

          <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex flex-col space-y-2 w-3/4">
            <button
              onClick={() => handleButtonClick("버튼 1")}
              className="w-full h-12 bg-blue-500 text-white rounded-lg"
            >
              알람 설정
            </button>
            <button
              onClick={() => handleButtonClick("버튼 2")}
              className="w-full h-12 bg-green-500 text-white rounded-lg"
            >
              날씨 정보
            </button>
            <button
              onClick={() => handleButtonClick("버튼 3")}
              className="w-full h-12 bg-yellow-500 text-white rounded-lg"
            >
              가격 정보
            </button>
            <button
              onClick={() => handleButtonClick("버튼 4")}
              className="w-full h-12 bg-red-500 text-white rounded-lg"
            >
              봄챗
            </button>
          </div>
        </div>
      );
    } else if (screen === "alert") {
      return (
        <div className="h-full relative">
          {/* Scrollable message area */}
          <div className={messageContainerStyle}>
            {messages.map((msg, index) => (
              <div
                key={index}
                className={`p-2 rounded-lg mb-2 ${
                  msg.type === "user" ? "bg-white text-black" : "bg-gray-100"
                }`}
              >
                {msg.content}
              </div>
            ))}
          </div>
    
          {/* Input section */}
          <div className="absolute bottom-0 left-0 w-full bg-white p-2">
            <div className="flex items-center w-full">
              <input
                type="text"
                className="w-full p-2 border rounded-lg"
                value={userInput}
                onChange={(e) => setUserInput(e.target.value)}
              />
              <button
                onClick={handleUserInputSubmit}
                className="w-24 h-10 bg-blue-500 text-white rounded-lg ml-2"
              >
                전송
              </button>
            </div>
          </div>
        </div>
      );
    } else if (screen === "weather") {
      return (
        <div className="h-full relative">
          {/* Scrollable message area */}
          <div className={messageContainerStyle}>
            {messages.map((msg, index) => (
              <div
                key={index}
                className={`p-2 rounded-lg mb-2 ${
                  msg.type === "user" ? "bg-white text-black" : "bg-gray-100"
                }`}
              >
                {msg.content}
              </div>
            ))}
          </div>

          {/* User input section */}
          {/* <div className="absolute bottom-0 left-0 w-full bg-white p-2 shadow-lg">
            <div className="flex items-center w-full">
              <input
                type="text"
                placeholder="품종과 가격을 입력하세요"
                className="w-full p-2 border rounded-l-lg"
                value={userInput}
                onChange={(e) => setUserInput(e.target.value)}
              />
              <button
                onClick={handleUserInputSubmit}
                className="w-24 h-10 bg-blue-500 text-white rounded-r-lg ml-2"
              >
                알림 설정
              </button>
            </div>
          </div> */}
        </div>
      );
    } else if (screen === "price") {
      return (
        <div className="h-full relative">
          {/* Scrollable message area */}
          <div className={messageContainerStyle}>
            {messages.map((msg, index) => (
              <div
                key={index}
                className={`p-2 rounded-lg mb-2 ${
                  msg.type === "user" ? "bg-white text-black" : "bg-gray-100"
                }`}
              >
                {msg.content}
              </div>
            ))}
          </div>
    
          {/* Input section */}
          <div className="absolute bottom-0 left-0 w-full bg-white p-2">
            <div className="flex items-center w-full">
              <input
                type="text"
                className="w-full p-2 border rounded-lg"
                value={userInput}
                onChange={(e) => setUserInput(e.target.value)}
              />
              <button
                onClick={handleUserInputSubmit}
                className="w-24 h-10 bg-blue-500 text-white rounded-lg ml-2"
              >
                전송
              </button>
            </div>
          </div>
        </div>
      );
    } else if (screen === "other") {
      return (
        <div className="h-full relative">
          {/* Scrollable message area */}
          <div className={messageContainerStyle}>
            {messages.map((msg, index) => (
              <div
                key={index}
                className={`p-2 rounded-lg mb-2 ${
                  msg.type === "user" ? "bg-white text-black" : "bg-gray-100"
                }`}
              >
                {msg.content}
              </div>
            ))}
          </div>
    
          {/* Input section */}
          <div className="absolute bottom-0 left-0 w-full bg-white p-2">
            <div className="flex items-center w-full">
              <input
                type="text"
                className="w-full p-2 border rounded-lg"
                value={userInput}
                onChange={(e) => setUserInput(e.target.value)}
              />
              <button
                onClick={handleUserInputSubmit}
                className="w-24 h-10 bg-blue-500 text-white rounded-lg ml-2"
              >
                전송
              </button>
            </div>
          </div>
        </div>
      );
    }
  };

  return (
    <div className="fixed bottom-16 right-6 bg-white w-80 h-[500px] shadow-lg rounded-lg p-4 flex flex-col">
      {/* Header section */}
      <div className="flex items-center mb-4 w-full justify-center">
        {screen !== "initial" && (
          <button
            onClick={handleBackButtonClick}
            className="bg-gray-200 text-gray-600 w-8 h-8 rounded-full absolute left-4"
          >
            ←
          </button>
        )}
        <p className="text-center font-bold flex-grow">봄넷 챗봇</p>
      </div>
  
      {/* Content section with flex-grow */}
      <div className="flex-1 overflow-hidden">
        {renderContent()}
      </div>
  
      {/* Close Button */}
      <button
        onClick={onClose}
        className="absolute top-2 right-2 bg-gray-200 text-gray-600 w-8 h-8 rounded-full"
      >
        ✕
      </button>
    </div>
  );
};

export default ChatbotPopup;