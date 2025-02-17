import React, { useState } from "react";
import { items } from "../../data_sample";
import { FiHelpCircle } from "react-icons/fi";

const ProductSearch = () => {
  const [query, setQuery] = useState("");
  const [legalQuery, setLegalQuery] = useState("");
  const [filteredItems, setFilteredItems] = useState(items);

  // 상품명 기준으로 아이템을 필터링하는 함수
  const filterItems = (prodQuery: string) => {
    setFilteredItems(
      items.filter((item) =>
        prodQuery ? item.name.toLowerCase().includes(prodQuery) : true
      )
    );
  };

  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value.toLowerCase();
    setQuery(input);
    filterItems(input);
  };

  const handleLegalCodeSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value.toLowerCase();
    setLegalQuery(input);
    // 법정동코드는 필터링에 사용하지 않고 상태에 저장만 합니다.
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">상품 검색</h2>
      <input
        type="text"
        value={query}
        onChange={handleSearch}
        placeholder="예: 사과"
        className="w-full border border-gray-300 rounded-t-lg px-4 py-2"
      />
      {(query || legalQuery) && (
        <ul className="max-h-40 overflow-y-auto border border-t-0 border-gray-300 rounded-b-lg">
          {filteredItems.map((item) => (
            <li key={item.id} className="p-2 hover:bg-gray-100 cursor-pointer">
              {item.name}
            </li>
          ))}
        </ul>
      )}
      <div className="mt-4">
      
        <input
          type="text"
          value={legalQuery}
          onChange={handleLegalCodeSearch}
          placeholder="법정동코드 입력"
          className="w-full border border-gray-300 rounded-lg px-4 py-2"
        />
      </div>

      <div className="mt-2 text-sm text-blue-500 flex items-center gap-1">
        <FiHelpCircle className="text-lg" /> {/* 물음표 아이콘 */}
        <a
          href="https://www.code.go.kr/stdcode/regCodeL.do"
          target="_blank"
          rel="noopener noreferrer"
          className="hover:underline"
        >
          법정동 코드 찾기
        </a>
      </div>
    </div>
  );
};

export default ProductSearch;
