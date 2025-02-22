import { useState } from "react";
import ChatbotPopup from "./ChatBotPopup";

const ChatBotButton = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <button
        onClick={() => setIsOpen(true)}
        className="fixed bottom-6 right-6 bg-green-500 text-white w-14 h-14 rounded-full shadow-lg"
      >
        💬
      </button>
      {isOpen && <ChatbotPopup onClose={() => setIsOpen(false)} />}
    </>
  );
};

export default ChatBotButton;
