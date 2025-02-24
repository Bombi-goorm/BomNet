import React, { useState } from "react";
import { ITEM_VARIETY_MAP } from "../../data_sample";
import { FiHelpCircle } from "react-icons/fi";

const ProductSearch = ({
  onSearch,
}: {
  onSearch: (data: { item: string; variety: string | null; pnu: string }) => void;
}) => {
  const [query, setQuery] = useState("");
  const [filteredItems, setFilteredItems] = useState(
    Array.from(new Set(ITEM_VARIETY_MAP.map((item) => item.midName))) // 중복 제거
  );
  const [selectedItem, setSelectedItem] = useState<string | null>(null);
  const [selectedVariety, setSelectedVariety] = useState<string | null>(null);
  const [pnuCode, setPnuCode] = useState("");

  // 품목 검색 처리
  const handleQueryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setQuery(input);

    // 품목 필터링 (중복 제거)
    const filtered = Array.from(
      new Set(
        ITEM_VARIETY_MAP.map((item) => item.midName).filter((name) =>
          name.toLowerCase().includes(input.toLowerCase())
        )
      )
    );
    setFilteredItems(filtered);
  };

  // 품목 선택 시 품종 필터링
  const getVarietyOptions = (itemName: string) => {
    return ITEM_VARIETY_MAP.filter((item) => item.midName === itemName).map(
      (item) => item.smallName
    );
  };

  // 품목 선택
  const handleItemSelect = (itemName: string) => {
    setSelectedItem(itemName);
    setSelectedVariety(null); // 품종 초기화
    setQuery(itemName); // 입력창에도 표시
    setFilteredItems([]); // 목록 숨기기
  };

  // 검색 실행
  const handleSearch = () => {
    if (!selectedItem) {
      alert("검색할 품목을 선택해주세요.");
      return;
    }

    onSearch({
      item: selectedItem,
      variety: selectedVariety,
      pnu: pnuCode,
    });
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">📌 상품 검색</h2>

      {/* 품목 검색 입력 */}
      <div className="relative">
        <input
          type="text"
          value={query}
          onChange={handleQueryChange}
          placeholder="예: 사과"
          className="w-full border border-gray-300 rounded-lg px-4 py-2"
        />
        {/* 검색 결과 목록 */}
        {query && (
          <ul className="absolute bg-white border border-gray-300 rounded-lg mt-1 w-full max-h-40 overflow-y-auto shadow-lg">
            {filteredItems.map((item, index) => (
              <li
                key={index}
                onClick={() => handleItemSelect(item)}
                className="p-2 cursor-pointer hover:bg-gray-100"
              >
                {item}
              </li>
            ))}
          </ul>
        )}
      </div>

      {/* 품종 선택 (품목이 선택된 경우에만 표시) */}
      {selectedItem && (
        <div className="mt-4">
          <label htmlFor="variety-select" className="text-sm text-gray-700">
            품종 선택:
          </label>
          <select
            id="variety-select"
            value={selectedVariety || ""}
            onChange={(e) => setSelectedVariety(e.target.value)}
            className="w-full border border-gray-300 rounded-lg px-4 py-2 mt-2"
          >
            <option value="">전체</option>
            {getVarietyOptions(selectedItem).map((variety, index) => (
              <option key={index} value={variety}>
                {variety}
              </option>
            ))}
          </select>
        </div>
      )}

      {/* PNU 코드 입력 */}
      <div className="mt-4">
        <label htmlFor="pnu-code" className="text-sm text-gray-700">
          PNU 코드 입력:
        </label>
        <input
          type="text"
          id="pnu-code"
          value={pnuCode}
          onChange={(e) => setPnuCode(e.target.value)}
          placeholder="예: 1168010300"
          className="w-full border border-gray-300 rounded-lg px-4 py-2 mt-2"
        />
      </div>

      {/* PNU 코드 도움말 */}
      <div className="mt-2 text-sm text-blue-500 flex items-center gap-1">
        <FiHelpCircle className="text-lg" />
        <a
          href="https://plprice.netlify.app/"
          target="_blank"
          rel="noopener noreferrer"
          className="hover:underline"
        >
          PNU 코드 찾기
        </a>
      </div>

      {/* 검색 버튼 */}
      <div className="mt-6 text-center">
        <button
          onClick={handleSearch}
          className="bg-green-500 text-white px-6 py-2 rounded-lg hover:bg-green-600 transition"
        >
          검색
        </button>
      </div>
    </div>
  );
};

export default ProductSearch;
