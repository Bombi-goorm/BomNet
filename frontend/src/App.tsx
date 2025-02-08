import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage"
import PricePage from "./pages/PricePage";
import ProductPage from "./pages/ProductPage";

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
        </Routes>
      </Router>

    </>
  )
}

export default App
