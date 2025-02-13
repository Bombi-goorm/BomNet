import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage"
import PricePage from "./pages/PricePage";
import ProductPage from "./pages/ProductPage";
import SignUpPage from "./pages/SignUpPage";
import LoginPage from "./pages/LoginPage";
import MyInfoPage from "./pages/MyInfoPage";
import AlarmListPage from "./pages/AlarmListPage";

function App() {

  return (
    <>
      <Router>
        <Routes>
          {/* 홈 페이지 */}
          <Route path="/" element={<HomePage />} />
          {/* 경락가 페이지 */}
          <Route path="/price" element={<PricePage />} />
          {/* 상품 페이지 */}
          <Route path="/product" element={<ProductPage />} />
          {/* 로그인 페이지 */}
          <Route path="/login" element={<LoginPage/>} />

          {/* 등록 페이지 */}
          <Route path="/signup" element={<SignUpPage  />} />

          {/* 정보 페이지 */}
          <Route path="/info" element={<MyInfoPage  />} />
          {/* 알림목록 페이지 */}
          <Route path="/alarm" element={<AlarmListPage  />} />
        </Routes>
      </Router>

    </>
  )
}

export default App
