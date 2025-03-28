// Header.tsx
import { Link } from "react-router-dom";
import { useAuth } from "../conntext_api/AuthProvider";

const Header = () => {
  const { isAuthenticated, isLoading, logout } = useAuth();

  if(isLoading) {
    // 인증 상태가 로드될 때까지 간단한 로딩 UI 또는 빈 헤더를 반환
    return <header className="p-4 bg-white shadow-md">Loading...</header>;
  }

  return (
    <header className="flex justify-between items-center p-4 bg-white shadow-md">
      <div className="text-green-500 font-bold text-xl">
        <Link to="/">봄넷</Link>
      </div>
      {isAuthenticated ? (
        <nav className="flex gap-4 text-gray-700">
          <Link to="/price" className="hover:text-green-500">유통정보</Link>
          <Link to="/product" className="hover:text-green-500">생산정보</Link>
          <Link to="/info" className="hover:text-green-500">사용자 정보</Link>
          <Link to="/alarm" className="hover:text-green-500">알림 목록</Link>
          <button 
            onClick={logout} 
            className="hover:text-green-500 focus:outline-none"
          >
            로그아웃
          </button>
        </nav>
      ) : (
        <nav className="flex gap-4 text-gray-700">
          <Link to="/price" className="hover:text-green-500">유통정보</Link>
          <Link to="/product" className="hover:text-green-500">생산정보</Link>
          <Link to="/login" className="hover:text-green-500">로그인</Link>
        </nav>
      )}
    </header>
  );
};

export default Header;