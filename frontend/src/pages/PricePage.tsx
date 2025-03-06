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

// 챗봇이나 검색에서 전달받는 품목 정보 타입
interface SelectedDataString {
  item: string;
}

const PricePage = () => {
  const location = useLocation();
  const chatItem = location.state as SelectedDataString | null;

  const [priceResponse, setPriceResponse] = useState<PriceResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  // 가격 데이터 조회
  useEffect(() => {
    const fetchPriceData = async () => {
      setLoading(true);

      try {
        const requestData: ProductRequestDto = {
          midName: chatItem?.item || "", // 품목 정보가 있으면 포함, 없으면 빈 값
        };

        const response: CommonResponseDto<PriceResponse> = await itemPriceSearch(requestData);

        if (response.status === "200") {
          setPriceResponse(response.data);
        } else {
          throw new Error(response.message || "데이터 조회 실패");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchPriceData();
  }, [chatItem]);

  return (
    <>
      <Header />
      <div className="font-sans bg-gray-50 min-h-screen">
        <main className="max-w-6xl mx-auto p-4">
          <SearchBar
            onSelect={() => {
              // SearchBar에서 품목 선택 시 추가 처리 가능 (예: URL 업데이트)
            }}
          />
          {loading && <p>데이터 로딩 중...</p>}
          {priceResponse && (
            <>
              <PriceHistoryChart priceData={priceResponse} />
              <AuctionPriceChart priceData={priceResponse} />
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