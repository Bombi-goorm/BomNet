import { Link } from "react-router-dom";
import { useAuth } from "../conntext_api/AuthProvider";

const Header = () => {
  const { isAuthenticated } = useAuth();

  return (
    <header className="flex justify-between items-center p-4 bg-white shadow-md">
      <div className="text-green-500 font-bold text-xl">
        <Link to="/">봄넷</Link>
      </div>

      {/* isAuthenticated 값에 따라 네비게이션 메뉴 표시 */}
      {isAuthenticated ? (
        <nav className="flex gap-4 text-gray-700">
          <Link to="/price" className="hover:text-green-500">가격정보</Link>
          <Link to="/product" className="hover:text-green-500">생산품정보</Link>
          <Link to="/signup" className="hover:text-green-500">등록</Link>
          <Link to="/info" className="hover:text-green-500">사용자 정보</Link>
          <Link to="/alarm" className="hover:text-green-500">알림 목록</Link>
        </nav>
      ) : (
        <nav className="flex gap-4 text-gray-700">
          <Link to="/price" className="hover:text-green-500">가격정보</Link>
          <Link to="/product" className="hover:text-green-500">생산품정보</Link>
          <Link to="/signup" className="hover:text-green-500">등록</Link>
          <Link to="/info" className="hover:text-green-500">사용자 정보</Link>
          <Link to="/alarm" className="hover:text-green-500">알림 목록</Link>
          <Link to="/login" className="hover:text-green-500">로그인</Link>
        </nav>
      )}
    </header>
  );
};

export default Header;
