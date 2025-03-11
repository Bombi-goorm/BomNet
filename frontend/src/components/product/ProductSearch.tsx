import React, { useState, useEffect, useRef } from "react";
import { ITEM_VARIETY_MAP } from "../../data_sample";
import { FiHelpCircle } from "react-icons/fi";

const ProductSearch: React.FC<{
  onSearch: (data: { item: string; variety: string; pnu: string }) => void;
}> = ({ onSearch }) => {
  const [query, setQuery] = useState("");
  const [filteredItems, setFilteredItems] = useState<string[]>([]);
  const [selectedItem, setSelectedItem] = useState<string>('');
  const [selectedVariety, setSelectedVariety] = useState<string>('');
  const [pnuCode, setPnuCode] = useState<string>("");

  const searchBarRef = useRef<HTMLDivElement | null>(null);

  // 세션스토리지에서 PNU 코드 불러오기
  useEffect(() => {
    const savedPnu = sessionStorage.getItem("bomnet_pnu");
    if (savedPnu) {
      setPnuCode(savedPnu);
    }
  }, []);

  // 외부 클릭 감지해서 리스트 닫기
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (searchBarRef.current && !searchBarRef.current.contains(event.target as Node)) {
        setFilteredItems([]);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // 품목 검색 처리
  const handleQueryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setQuery(input);

    if (input.trim() === "") {
      setFilteredItems([]);
    } else {
      const filtered = Array.from(
        new Set(
          ITEM_VARIETY_MAP.map((item) => item.midName).filter((name) =>
            name.toLowerCase().includes(input.toLowerCase())
          )
        )
      );
      setFilteredItems(filtered);
    }
  };

  // 품목 선택 시: 입력창 업데이트 및 리스트 닫기
  const handleItemSelect = (itemName: string) => {
    setSelectedItem(itemName);
    setSelectedVariety('');
    setQuery(itemName);
    setFilteredItems([]);
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
    <div ref={searchBarRef} className="bg-white p-6 rounded-lg shadow relative">
      <h2 className="text-xl font-semibold mb-4">📌 상품 검색</h2>

      {/* 품목 검색 입력 */}
      <div className="relative flex items-center w-full">
        <input
          type="text"
          value={query}
          onChange={handleQueryChange}
          placeholder="예: 사과"
          className="w-full border border-gray-300 rounded-lg px-4 py-2 pr-24 transition-all"
        />
      </div>

      {/* 검색 결과 목록 */}
      {filteredItems.length > 0 && (
        <ul
          className="absolute left-0 w-full bg-white border border-gray-300 rounded-lg mt-2 max-h-40 overflow-y-auto shadow-lg z-50 transition-all duration-200 ease-in-out"
          style={{
            opacity: filteredItems.length > 0 ? 1 : 0,
            transform: filteredItems.length > 0 ? "translateY(0)" : "translateY(-10px)",
            pointerEvents: filteredItems.length > 0 ? "auto" : "none",
          }}
        >
          {filteredItems.map((item, index) => (
            <li
              key={index}
              onClick={() => handleItemSelect(item)}
              className="p-2 cursor-pointer hover:bg-gray-100 transition"
            >
              {item}
            </li>
          ))}
        </ul>
      )}

      {/* 품종 선택 (선택된 품목이 있는 경우) */}
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
            {ITEM_VARIETY_MAP.filter((item) => item.midName === selectedItem)
              .map((item) => item.smallName)
              .map((variety, index) => (
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