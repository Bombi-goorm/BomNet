import { Link } from "react-router-dom";

const Header = () => {
  return (
    <header className="flex justify-between items-center p-4 bg-white shadow-md">
      <div className="text-green-500 font-bold text-xl">
        <Link to="/">봄넷</Link>
      </div>
      <nav className="flex gap-4 text-gray-700">
        <Link to="/price" className="hover:text-green-500">
          가격정보
        </Link>
        <a href="/product" className="hover:text-green-500">
          생산품정보
        </a>
        <a href="#!" className="hover:text-green-500">
          로그인
        </a>
      </nav>
    </header>
  );
};

export default Header;
