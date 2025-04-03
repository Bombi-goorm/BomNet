import { useState } from "react";
import ChatbotPopup from "./ChatBotPopup";

const ChatBotButton = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      {/* 🔹 말풍선 (챗봇 안내) */}
      {!isOpen && (
        <div className="fixed bottom-24 right-6 bg-white text-gray-800 px-3 py-1 rounded-lg shadow-lg text-sm animate-bounce">
          💬 "챗봇에게 물어보세요!"
        </div>
      )}

      <button
        onClick={() => setIsOpen(true)}
        className="fixed bottom-6 right-6 bg-green-500 text-white w-16 h-16 rounded-full shadow-lg flex items-center justify-center overflow-hidden"
      >
        {/* 🔹 챗봇 캐릭터 */}
        <img
          src="/bomi.png"
          alt="Chatbot"
          className="w-20 h-20 object-contain"
        />
      </button>

      {isOpen && <ChatbotPopup onClose={() => setIsOpen(false)} />}
    </>
  );
};

export default ChatBotButton;