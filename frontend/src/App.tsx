import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import PricePage from "./pages/PricePage";
import ProductPage from "./pages/ProductPage";
import SignUpPage from "./pages/SignUpPage";
import LoginPage from "./pages/LoginPage";
import MyInfoPage from "./pages/MyInfoPage";
import AlarmListPage from "./pages/AlarmListPage";
import ERR404 from "./pages/err/ERR404";
import ERR500 from "./pages/err/ERR500";
import { useEffect } from "react";

const registerServiceWorker = async () => {
  if ("serviceWorker" in navigator) {
    try {
      const registration = await navigator.serviceWorker.register("/sw.js"); // ✅ `.ts` → `.js`
      console.log("✅ Service Worker Registered:", registration);
    } catch (error) {
      console.error("❌ Service Worker Registration Failed:", error);
    }
  } else {
    console.warn("⚠️ 서비스 워커를 지원하지 않는 브라우저입니다.");
  }
};

function App() {
  // ✅ useEffect는 반드시 함수 컴포넌트 내부에서 실행해야 함
  useEffect(() => {
    registerServiceWorker();
  }, []);

  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignUpPage />} />

      <Route path="/price" element={<PricePage />} />
      <Route path="/product" element={<ProductPage />} />

      <Route path="/404" element={<ERR404 />} />
      <Route path="/500" element={<ERR500 />} />

      {/* <Route element={<ProtectedRoute />}> */}
      <Route path="/info" element={<MyInfoPage />} />
      <Route path="/alarm" element={<AlarmListPage />} />
      {/* </Route> */}
    </Routes>
  );
}

export default App;