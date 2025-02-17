import { useState } from "react";
import Header from "../components/Header";
import { useNavigate } from "react-router-dom";

interface TooltipProps {
  content: string;
  link?: string;
  linkText?: string;
}

const Tooltip: React.FC<TooltipProps> = ({ content, link, linkText }) => {
  const [visible, setVisible] = useState(false);

  return (
    <span
      className="relative inline-block"
      onMouseEnter={() => setVisible(true)}
      onMouseLeave={() => setVisible(false)}
    >
      <span className="ml-1 inline-block w-5 h-5 rounded-full bg-blue-500 text-white text-xs flex items-center justify-center cursor-pointer">
        ?
      </span>
      {visible && (
        <div className="absolute z-10 p-2 bg-gray-700 text-white text-xs rounded w-72 left-0 bottom-full mb-0">
          <p>{content}</p>
          {link && (
            <a
              href={link}
              target="_blank"
              rel="noopener noreferrer"
              className="underline text-blue-300"
            >
              {linkText || "자세히 보기"}
            </a>
          )}
        </div>
      )}
    </span>
  );
};

function SignupPage() {
  const navigate = useNavigate();
  const [additionalInfo1, setAdditionalInfo1] = useState("");
  const [additionalInfo2, setAdditionalInfo2] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setErrorMessage("");
    setLoading(true);

    try {
      const response = await fetch(`${import.meta.env.VITE_SERVER_URL}/api/signup`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ additionalInfo1, additionalInfo2 }),
      });

      if (response.ok) {
        navigate("/");
      } else {
        const errorData = await response.json();
        setErrorMessage(errorData.message || "가입에 실패했습니다. 다시 시도해 주세요.");
      }
    } catch (error) {
      console.error("Signup error:", error);
      setErrorMessage("서버와의 통신 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  // 두 입력창 모두 채워졌는지 확인 (공백 제거)
  const isFilled = additionalInfo1.trim() !== "" && additionalInfo2.trim() !== "";

  // 버튼 문구: 두 입력창 모두 입력되었으면 "농업인 등록하기", 아니면 "일반 사용자 등록하기"
  const buttonText =
    loading
      ? "가입 진행 중..."
      : isFilled
      ? "농업인 등록하기"
      : "일반 사용자 등록하기";

  return (
    <>
      <Header />
      <div className="font-sans bg-gray-50 min-h-screen flex items-center justify-center p-4">
        <div className="bg-white p-8 rounded shadow-md w-full max-w-md md:max-w-lg lg:max-w-xl mx-auto">
          <h2 className="text-2xl font-bold mb-4 text-center">처음이시네요!</h2>
          <p className="text-center text-gray-700 mb-6">
            농업인이시라면 추가 정보를 입력하신 후 맞춤형 정보를 받아보세요.
          </p>
          <form onSubmit={handleSubmit} className="space-y-4">
            {/* <div>
              <label className="block text-sm font-medium text-gray-700">
                농지 법정동 코드
                <Tooltip
                  content="법정동 코드는 행정 구역을 식별하기 위한 코드입니다."
                  link="https://www.code.go.kr/stdcode/regCodeL.do"
                  linkText="법정동 코드 확인하기"
                />
              </label>
              <input
                type="text"
                value={additionalInfo1}
                onChange={(e) => setAdditionalInfo1(e.target.value)}
                placeholder="예: 1111010100"
                required
                className="w-full border border-gray-300 p-2 rounded mt-1 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div> */}
            <div>
              <label className="block text-sm font-medium text-gray-700">
                농지 PNU 코드
                <Tooltip
                  link="https://plprice.netlify.app/"
                  linkText="PNU 코드 확인하기"
                  content="PNU 코드는 필지고유번호로, 토지 및 건물의 정보를 관리하기 위한 고유 식별자입니다."
                />
              </label>
              <input
                type="text"
                value={additionalInfo2}
                onChange={(e) => setAdditionalInfo2(e.target.value)}
                placeholder="예: 1111018300101970001"
                required
                className="w-full border border-gray-300 p-2 rounded mt-1 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            {errorMessage && (
              <p className="text-red-500 text-center">{errorMessage}</p>
            )}
            {/* 입력창이 채워지지 않았을 때만 안내 문구 표시 */}
            {!isFilled && (
              <p className="text-center text-sm text-gray-500">
                일반 사용자라면 정보 입력 없이 바로 등록 가능해요.
              </p>
            )}
            <button
              type="submit"
              disabled={loading}
              className={`w-full text-white p-3 rounded mt-4 disabled:opacity-50 disabled:cursor-not-allowed ${
                isFilled ? "bg-green-500 hover:bg-green-600" : "bg-blue-500 hover:bg-blue-600"
              }`}
            >
              {buttonText}
            </button>
          </form>
        </div>
      </div>
    </>
  );
}

export default SignupPage;