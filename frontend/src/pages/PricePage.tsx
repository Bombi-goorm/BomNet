import { useEffect, useState } from "react";
import SearchBar from "../components/price/SearchBar";
import SelectedItems from "../components/price/SelectedItems";
import MultiLineChart from "../components/price/MultiLineChart";
import Header from "../components/Header";
import { items, varieties, regions, pupularProducts } from "../data_sample";

// 품목 및 품종 매핑 데이터셋
export const ITEM_VARIETY_MAP = [
  { item: "사과", itemCode: "1001", variety: "부사", varietyCode: "2001" },
  { item: "사과", itemCode: "1001", variety: "홍로", varietyCode: "2002" },
  { item: "배추", itemCode: "1002", variety: "고랭지배추", varietyCode: "2003" },
  { item: "배추", itemCode: "1002", variety: "월동배추", varietyCode: "2004" },
  { item: "상추", itemCode: "1003", variety: "청상추", varietyCode: "2005" },
  { item: "상추", itemCode: "1003", variety: "적상추", varietyCode: "2006" },
];



// SearchBar에서 전달받는 데이터 타입 (문자열 기반)
interface SelectedDataString {
  item: string;
  variety: string;
  region: string;
}

// MultiLineChart가 기대하는 데이터 타입 (id 기반)
interface SelectedData {
  itemId: number;
  varietyId: number;
  regionId: number;
}

// 기본 날짜 계산 함수 (SearchBar와 동일한 로직)
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

  // 선택된 데이터를 문자열 기반으로 저장
  const [selectedItems, setSelectedItems] = useState<SelectedDataString[]>([]);
  const [startDate, setStartDate] = useState<string>(oneWeekAgo);
  const [endDate, setEndDate] = useState<string>(today);

  // 가장 인기있는 상품을 샘플로 항상 제공
  useEffect(() => {
    const defaultProduct = pupularProducts[0];
    if (defaultProduct) {
      const defaultVariety = varieties.find((v) => v.id === defaultProduct.varietyId);
      const defaultItemName = defaultVariety
        ? items.find((i) => i.id === defaultVariety.itemId)?.name || ""
        : "";
      const defaultVarietyName = defaultVariety ? defaultVariety.name : "";
      // 기본 지역
      const defaultRegionName = regions[0].name;
      
      setSelectedItems([
        {
          item: defaultItemName,
          variety: defaultVarietyName,
          region: defaultRegionName,
        },
      ]);
    }
  }, []);

  const handleFilterChange = (selectedData: SelectedDataString) => {
    if (selectedItems.length < 5) {
      setSelectedItems((prev) => [...prev, selectedData]);
    } else {
      alert("최대 5개의 품목만 저장 가능합니다!");
    }
  };

  const handleRemoveItem = (index: number) => {
    setSelectedItems((prev) => prev.filter((_, i) => i !== index));
  };

  const handleDateChange = (start: string, end: string) => {
    setStartDate(start);
    setEndDate(end);
  };

  // MultiLineChart에 전달하기 전에 선택 데이터를 id 기반으로 변환
  const selectionsWithIds: SelectedData[] = selectedItems.map((selected) => ({
    itemId: items.find((i) => i.name === selected.item)?.id || 0,
    varietyId: varieties.find((v) => v.name === selected.variety)?.id || 0,
    regionId: regions.find((r) => r.name === selected.region)?.id || 0,
  }));


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
