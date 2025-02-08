const ChatbotPopup = ({ onClose }: { onClose: () => void }) => {
  return (
    <div className="fixed bottom-16 right-6 bg-white w-80 h-96 shadow-lg rounded-lg p-4">
      <button
        onClick={onClose}
        className="absolute top-2 right-2 bg-gray-200 text-gray-600 w-8 h-8 rounded-full"
      >
        ✕
      </button>
      <p className="text-center font-bold mb-2">봄넷 챗봇</p>
      <div className="h-full overflow-y-auto">
        <p className="p-2 bg-gray-100 rounded-lg mb-2">안녕하세요! 무엇을 도와드릴까요?</p>
        <input
          type="text"
          placeholder="질문을 입력하세요..."
          className="w-full mt-4 p-2 border rounded"
        />
      </div>
    </div>
  );
};

export default ChatbotPopup;
