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

function App() {
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
