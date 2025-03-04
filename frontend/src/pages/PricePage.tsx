import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import SearchBar from "../components/price/SearchBar";
import SelectedItems from "../components/price/SelectedItems";
import MultiLineChart from "../components/price/MultiLineChart";
import Header from "../components/Header";
import { ITEM_VARIETY_MAP, REGIONS } from "../data_sample";

// SearchBar에서 전달받는 데이터 타입 (문자열 기반)
interface SelectedDataString {
  midName: string;
  smallName: string;
  region: string;
}

// MultiLineChart가 기대하는 데이터 타입 (id 기반)
interface SelectedData {
  smallId: string;
  regionId: number;
}

// 기본 날짜 계산 함수
const getDefaultDates = () => {
  const today = new Date();
  const oneWeekAgo = new Date();
  oneWeekAgo.setDate(today.getDate() - 7);

  const formatDate = (date: Date) => date.toISOString().split("T")[0];

  return {
    today: formatDate(today),
    oneWeekAgo: formatDate(oneWeekAgo),
  };
};

const PricePage = () => {
  const { today, oneWeekAgo } = getDefaultDates();
  const location = useLocation(); // 챗봇에서 전달된 데이터 확인
  const chatbotData = location.state; // 챗봇에서 보낸 품목/품종 데이터

  // ✅ 임시 저장 목록 (메모리에서 관리)
  const [selectedItems, setSelectedItems] = useState<SelectedDataString[]>([]);
  const [startDate, setStartDate] = useState<string>(oneWeekAgo);
  const [endDate, setEndDate] = useState<string>(today);

  // 🔹 **기본 추천 상품 or 챗봇에서 넘어온 상품 처리**
  useEffect(() => {
    if (chatbotData) {
      // 챗봇에서 넘어온 데이터가 있을 경우, 이를 기반으로 설정
      setSelectedItems([
        {
          midName: chatbotData.midName,
          smallName: chatbotData.smallName,
          region: "서울", // 기본 지역
        },
      ]);
    } else {
      // 기본 추천 품목 설정
      fetchRecommendedProducts();
    }
  }, [chatbotData]);

  // 🔹 **기본 추천 상품을 DB에서 불러오기**
  const fetchRecommendedProducts = async () => {
    try {
      // API 요청 (예시: "/api/recommendations")
      await fetch("/core/recommendations");

      const data = ITEM_VARIETY_MAP

      if (data.length > 0) {
        setSelectedItems([
          {
            midName: data[0].midName,
            smallName: data[0].smallName,
            region: "서울", // 기본 지역
          },
        ]);
      }
    } catch (error) {
      console.error("추천 상품 불러오기 실패:", error);
    }
  };

  // 🔹 **필터 변경 처리 (품목, 품종, 지역 선택)**
  const handleFilterChange = (selectedData: SelectedDataString) => {
    // 🔍 중복 검사 (midName, smallName, region이 같은 항목이 있는지 체크)
    const isDuplicate = selectedItems.some(
      (item) =>
        item.midName === selectedData.midName &&
        item.smallName === selectedData.smallName &&
        item.region === selectedData.region
    );
  
    if (isDuplicate) {
      alert("⚠️ 이미 추가된 품목입니다!");
      return;
    }
  
    if (selectedItems.length < 5) {
      setSelectedItems((prev) => [...prev, selectedData]);
    } else {
      alert("최대 5개의 품목만 저장 가능합니다!");
    }
  };

  // 🔹 **선택 품목 제거**
  const handleRemoveItem = (index: number) => {
    setSelectedItems((prev) => prev.filter((_, i) => i !== index));
  };

  // 🔹 **날짜 변경 처리**
  const handleDateChange = (start: string, end: string) => {
    setStartDate(start);
    setEndDate(end);
  };

  // 🔹 **선택 데이터를 id 기반으로 변환하여 MultiLineChart에 전달**
  const selectionsWithIds: SelectedData[] = selectedItems.map((selected) => {
    const matchedVariety = ITEM_VARIETY_MAP.find(
      (entry) =>
        entry.midName === selected.midName && entry.smallName === selected.smallName
    );
    const matchedRegion = REGIONS.find((r) => r.name === selected.region);

    return {
      smallId: matchedVariety ? matchedVariety.smallId : "00",
      regionId: matchedRegion ? matchedRegion.regionId : 1, // 기본 서울
    };
  });

  return (
    <>
      <Header />
      <div className="font-sans bg-gray-50 min-h-screen">
        <main className="max-w-6xl mx-auto p-4">
          <SearchBar
            onFilterChange={handleFilterChange}
            onDateChange={handleDateChange}
          />
          <SelectedItems items={selectedItems} onRemoveItem={handleRemoveItem} />
          <MultiLineChart
            selections={selectionsWithIds}
            startDate={startDate}
            endDate={endDate}
          />
        </main>
      </div>
    </>
  );
};

export default PricePage;
