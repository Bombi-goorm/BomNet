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

// 🔹 **SearchBar에서 선택된 품목을 PricePage에서 관리**
const PricePage = () => {
  const location = useLocation();
  const chatItem = location.state as { item: string } | null;

  const [priceResponse, setPriceResponse] = useState<PriceResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [selectedItem, setSelectedItem] = useState<string>(chatItem?.item || "");

  // 🔹 **가격 데이터 조회 함수**
  const fetchPriceData = async (item: string) => {
    setLoading(true);
    try {
      const requestData: ProductRequestDto = { item };

      const response: CommonResponseDto<PriceResponse> = await itemPriceSearch(requestData);

      if (response.status === "200") {
        setPriceResponse(response.data);
      } else {
        alert("데이터 조회 실패")
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (selectedItem) fetchPriceData(selectedItem);
  }, [selectedItem]);

  return (
    <>
      <Header />
      <div className="font-sans bg-gray-50 min-h-screen">
        <main className="max-w-6xl mx-auto p-4">
          <SearchBar onSelect={(item) => setSelectedItem(item)} />

          {loading && <p>데이터 로딩 중...</p>}
          {priceResponse && (
            <>
              <AuctionPriceChart priceData={priceResponse} />
              <PriceHistoryChart priceData={priceResponse} />
              <QualityChart priceData={priceResponse} />
              <RegionalPriceChart priceData={priceResponse} />
              <SankeyChart priceData={priceResponse} />
            </>
          )}
        </main>
      </div>
    </>
  );
};

export default PricePage;