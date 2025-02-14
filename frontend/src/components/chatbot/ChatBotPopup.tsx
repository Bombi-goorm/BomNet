import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface ChatMessage {
  id: number;
  sender: 'user' | 'bot';
  text: string;
}

const ChatbotPopup = ({ onClose }: { onClose: () => void }) => {
  const [messages, setMessages] = useState<ChatMessage[]>([
    { id: 1, sender: 'bot', text: '안녕하세요! 무엇을 도와드릴까요?' },
  ]);
  const [input, setInput] = useState('');
  const navigate = useNavigate();

  // 메시지 추가 함수
  const addMessage = (sender: 'user' | 'bot', text: string) => {
    const newMessage: ChatMessage = {
      id: messages.length + 1,
      sender,
      text,
    };
    setMessages(prev => [...prev, newMessage]);
  };

  // 사용자가 메시지를 전송할 때 처리
  const handleSend = () => {
    if (!input.trim()) return;
    addMessage('user', input);
    processUserInput(input);
    setInput('');
  };

  // 사용자의 입력을 분석하여 각 기능에 따른 분기 처리
  const processUserInput = (text: string) => {
    const lowerText = text.toLowerCase();

    // 1. 품목 가격 검색: 예) "사과 가격", "바나나 가격"
    if (lowerText.includes('가격') && /(사과|배|바나나|포도)/.test(lowerText)) {
      addMessage('bot', "입력하신 품목의 인기 품종 정보를 검색 중입니다...");
      // API 호출이나 데이터 처리 로직 추가 가능
      setTimeout(() => {
        addMessage('bot', "검색 결과가 준비되었습니다. 가격 페이지로 이동합니다.");
        navigate(`/price?item=${encodeURIComponent(text)}`);
      }, 1000);
      return;
    }

    // 2. 품종 가격 검색: 예) "특정 품종 가격", "xx 품종 가격정보"
    if (lowerText.includes('품종') && lowerText.includes('가격')) {
      addMessage('bot', "입력하신 품종의 가격 정보를 검색 중입니다...");
      setTimeout(() => {
        addMessage('bot', "검색 결과가 준비되었습니다. 가격 페이지로 이동합니다.");
        navigate(`/price?variety=${encodeURIComponent(text)}`);
      }, 1000);
      return;
    }

    // 3. 가격 알림 설정: 예) "사과 가격 알림 설정"
    if (lowerText.includes('알림') && lowerText.includes('가격')) {
      addMessage('bot', "가격 알림 설정을 진행 중입니다...");
      setTimeout(() => {
        addMessage('bot', "설정이 완료되었습니다. 앞으로 가격 변동 시 알림을 보내드리겠습니다.");
      }, 1000);
      return;
    }

    // 4. 적합 작물 추천 (농업인): 법정동 코드 입력
    if (lowerText.includes('법정동')) {
      addMessage('bot', "입력하신 법정동 코드를 기반으로 적합 작물을 추천 중입니다...");
      setTimeout(() => {
        addMessage('bot', "추천된 작물 정보가 준비되었습니다. 상품 페이지로 이동합니다.");
        navigate(`/product?crop=recommended`); // 추천 작물 파라미터 전달
      }, 1000);
      return;
    }

    // 5. 적합 작물 추천 (일반 사용자): 주소 입력
    if (lowerText.includes('주소')) {
      addMessage('bot', "입력하신 주소 정보를 기반으로 적합 작물을 추천 중입니다...");
      setTimeout(() => {
        addMessage('bot', "추천된 작물 정보가 준비되었습니다. 상품 페이지로 이동합니다.");
        navigate(`/product?crop=recommended`);
      }, 1000);
      return;
    }

    // 6. 정책, 법령 등 요청: 예) "정책", "법령"
    if (lowerText.includes('정책') || lowerText.includes('법령')) {
      addMessage('bot', "정책 및 법령 정보를 검색 중입니다...");
      setTimeout(() => {
        addMessage('bot', "검색 결과가 준비되었습니다. 정보 페이지로 이동합니다.");
        navigate(`/info?type=policy`);
      }, 1000);
      return;
    }

    // 7. 병해충 정보 요청: 예) "병해충", "병해"와 "충" 포함
    if (lowerText.includes('병해충') || (lowerText.includes('병해') && lowerText.includes('충'))) {
      addMessage('bot', "입력하신 작물의 병해충 정보를 검색 중입니다...");
      setTimeout(() => {
        addMessage('bot', "검색 결과가 준비되었습니다. 병해충 정보 페이지로 이동합니다.");
        navigate(`/info?type=pest`);
      }, 1000);
      return;
    }

    // 인식되지 않은 입력에 대한 기본 응답
    addMessage('bot', "죄송합니다. 이해하지 못했습니다. 다시 한 번 말씀해주시겠어요?");
  };

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
        {messages.map(message => (
          <p
            key={message.id}
            className={`p-2 rounded-lg mb-2 ${
              message.sender === 'bot'
                ? 'bg-gray-100 text-black'
                : 'bg-blue-100 text-black text-right'
            }`}
          >
            {message.text}
          </p>
        ))}
        <div className="mt-4 flex">
          <input
            type="text"
            value={input}
            onChange={e => setInput(e.target.value)}
            placeholder="질문을 입력하세요..."
            className="w-full p-2 border rounded"
            onKeyDown={e => {
              if (e.key === 'Enter') {
                handleSend();
              }
            }}
          />
          <button onClick={handleSend} className="ml-2 p-2 bg-blue-500 text-white rounded">
            전송
          </button>
        </div>
      </div>
    </div>
  );
};

export default ChatbotPopup;