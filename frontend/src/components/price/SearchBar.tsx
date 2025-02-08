import { useState } from "react";
import { items, varieties, regions } from "../../data_sample";

const SearchBar = ({
  onFilterChange,
  onDateChange,
}: {
  onFilterChange: (selectedData: {
    item: string;
    variety: string;
    region: string;
  }) => void;
  onDateChange: (startDate: string, endDate: string) => void;
}) => {
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

  const { today, oneWeekAgo } = getDefaultDates();
  const [query, setQuery] = useState("");
  const [filteredItems, setFilteredItems] = useState(items); // `items` 데이터 사용
  const [selectedItem, setSelectedItem] = useState<string | null>(null);
  const [selectedVariety, setSelectedVariety] = useState<string | null>(null);
  const [selectedRegion, setSelectedRegion] = useState<string | null>(null);
  const [startDate, setStartDate] = useState(oneWeekAgo);
  const [endDate, setEndDate] = useState(today);

  const handleQueryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setQuery(input);

    // 품목 필터링
    setFilteredItems(
      items.filter((item) =>
        item.name.toLowerCase().includes(input.toLowerCase())
      )
    );
  };

  const handleAdd = () => {
    if (selectedItem && selectedVariety && selectedRegion) {
      onFilterChange({
        item: selectedItem,
        variety: selectedVariety,
        region: selectedRegion,
      });

      // 초기화
      setQuery("");
      setFilteredItems(items); // 초기화 시 전체 품목 목록 복원
      setSelectedItem(null);
      setSelectedVariety(null);
      setSelectedRegion(null);
    } else {
      alert("품목, 품종, 지역을 모두 선택해주세요!");
    }
  };

  const handleDateChange = () => {
    console.log("Date Changed:", { startDate, endDate });
    onDateChange(startDate, endDate);
  };

  const getVarietyOptions = (itemName: string) => {
    const item = items.find((i) => i.name === itemName);
    if (!item) {
      console.log(`Item not found for ${itemName}`);
      return [];
    }
    return varieties.filter((v) => v.itemId === item.id);
  };

  return (
    <div className="bg-white p-4 rounded-lg shadow mb-6">
      {/* 날짜 선택 */}
      <div className="flex items-center space-x-4 mb-4">
        <div>
          <label htmlFor="start-date" className="text-sm text-gray-700">
            시작 날짜:
          </label>
          <input
            type="date"
            id="start-date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
            onBlur={handleDateChange}
            className="border border-gray-300 rounded-lg px-4 py-2 ml-2"
          />
        </div>
        <span className="text-gray-600">~</span>
        <div>
          <label htmlFor="end-date" className="text-sm text-gray-700">
            종료 날짜:
          </label>
          <input
            type="date"
            id="end-date"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
            onBlur={handleDateChange}
            className="border border-gray-300 rounded-lg px-4 py-2 ml-2"
          />
        </div>
      </div>

      {/* 검색 필터 */}
      <div className="flex flex-col space-y-4">
        {/* 품목 검색 */}
        <div>
          <label htmlFor="item-search" className="text-sm text-gray-700">
            품목 검색:
          </label>
          <input
            type="text"
            id="item-search"
            value={query}
            onChange={handleQueryChange}
            placeholder="예: 사과"
            className="w-full border border-gray-300 rounded-lg px-4 py-2 mt-2"
          />
          {/* 검색 결과 */}
          {query && (
            <ul className="bg-white border border-gray-300 rounded-lg mt-2 max-h-40 overflow-y-auto">
              {filteredItems.map((item) => (
                <li
                  key={item.id}
                  onClick={() => setSelectedItem(item.name)}
                  className={`p-2 cursor-pointer hover:bg-gray-100 ${
                    selectedItem === item.name ? "bg-green-100" : ""
                  }`}
                >
                  {item.name}
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* 품종 선택 */}
        {selectedItem && (
          <div>
            <label htmlFor="variety-select" className="text-sm text-gray-700">
              품종 선택:
            </label>
            <select
              id="variety-select"
              value={selectedVariety || ""}
              onChange={(e) => setSelectedVariety(e.target.value)}
              className="w-full border border-gray-300 rounded-lg px-4 py-2 mt-2"
            >
              <option value="">품종을 선택하세요</option>
              {getVarietyOptions(selectedItem).map((variety) => (
                <option key={variety.id} value={variety.name}>
                  {variety.name}
                </option>
              ))}
            </select>
          </div>
        )}

        {/* 지역 선택 */}
        <div>
          <label htmlFor="region-select" className="text-sm text-gray-700">
            지역 선택:
          </label>
          <select
            id="region-select"
            value={selectedRegion || ""}
            onChange={(e) => setSelectedRegion(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 mt-2"
          >
            <option value="">지역을 선택하세요</option>
            {regions.map((region) => (
              <option key={region.id} value={region.name}>
                {region.name}
              </option>
            ))}
          </select>
        </div>

        {/* 추가 버튼 */}
        <div className="text-center">
          <button
            onClick={handleAdd}
            className="bg-green-500 text-white px-6 py-2 rounded-lg hover:bg-green-600 transition"
          >
            추가
          </button>
        </div>
      </div>
    </div>
  );
};

export default SearchBar;
