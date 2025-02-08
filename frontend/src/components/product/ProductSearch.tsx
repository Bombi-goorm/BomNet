import React, { useState } from "react";
import { items } from "../../data_sample";

const ProductSearch = () => {
  const [query, setQuery] = useState("");
  const [filteredItems, setFilteredItems] = useState(items);

  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value.toLowerCase();
    setQuery(input);

    // 입력값이 있을 때만 필터링 수행
    if (input) {
      setFilteredItems(
        items.filter((item) => item.name.toLowerCase().includes(input))
      );
    } else {
      setFilteredItems([]);
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-xl font-semibold mb-4">상품 검색</h2>
      <input
        type="text"
        value={query}
        onChange={handleSearch}
        placeholder="예: 사과"
        className="w-full border border-gray-300 rounded-lg px-4 py-2"
      />
      {query && (
        <ul className="mt-4 max-h-40 overflow-y-auto border border-gray-300 rounded-lg">
          {filteredItems.map((item) => (
            <li key={item.id} className="p-2 hover:bg-gray-100 cursor-pointer">
              {item.name}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ProductSearch;
