import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import SearchBar from "../components/price/SearchBar";
import Header from "../components/Header";
import { PriceResponse } from "../types/price_types";
import AuctionPriceChart from "../components/price/AuctionPriceChart";
import PriceHistoryChart from "../components/price/PriceHistoryChart";
import QualityChart from "../components/price/QualityChart";
import RegionalPriceChart from "../components/price/RegionalPriceChart";
import SankeyChart from "../components/price/SankeyChart";
import { itemPriceSearch } from "../api/core_api";
import { CommonResponseDto } from "../types/member_types";
import { ProductRequestDto } from "../types/product_types";
import { priceResponse1 } from "../data_sample";

const PricePage = () => {
  const location = useLocation();
  const chatItem = location.state as string  | null;

  const [priceResponse, setPriceResponse] = useState<PriceResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [selectedItem, setSelectedItem] = useState<string>(""); // 🔹 초기값을 빈 문자열로 설정

  // 🔹 **가격 데이터 조회 함수**
  const fetchPriceData = async (item: string) => {
    if (!item) return; // 🔹 item이 빈 값일 경우 요청 방지

    const priceData = priceResponse1;

    setLoading(true);
    try {
      const requestData: ProductRequestDto = { item };
      const response: CommonResponseDto<PriceResponse> = await itemPriceSearch(requestData);
      

      if (response.status === "200") {
        setPriceResponse(response.data);
      } else {
        alert("데이터 조회 실패");
      }
    } catch (error) {
      console.error("❌ 데이터 조회 중 오류 발생:", error);
    } finally {
      setLoading(false);
    }
  };

  // 🔹 `chatItem`이 변경되었을 때 `selectedItem`을 업데이트하고 서버 요청
  useEffect(() => {
    if (chatItem) {
      setSelectedItem(chatItem);
      fetchPriceData(chatItem);
    }
  }, [chatItem]); // 🔹 chatItem 변경 감지

  // 🔹 `selectedItem`이 변경될 때 서버 요청 (SearchBar에서 선택했을 때도 실행됨)
  useEffect(() => {
    if (selectedItem) {
      fetchPriceData(selectedItem);
    }
  }, [selectedItem]);

  return (
    <>
      <Header />
      <div className="font-sans bg-gray-50 min-h-screen">
        <main className="max-w-6xl mx-auto p-4">
          {/* ✅ SearchBar에 `selectedItem` 값 전달하여 챗봇 값 반영 */}
          <SearchBar value={selectedItem} onSelect={(item) => setSelectedItem(item)} />

          {loading &&  
            <div className="flex items-center justify-center h-screen">
              <div className="spinner"></div>
            </div>}
          {priceResponse && (
            <>
              <AuctionPriceChart priceData={priceResponse1} />
              <PriceHistoryChart priceData={priceResponse1} />
              <QualityChart priceData={priceResponse1} />
              <RegionalPriceChart priceData={priceResponse1} />
              <SankeyChart priceData={priceResponse} />
            </>
          )}
        </main>
      </div>
    </>
  );
};

export default PricePage;