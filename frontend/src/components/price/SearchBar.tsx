import { useState, useEffect, useRef } from "react";

interface SearchBarProps {
  value: string; // 🔹 부모로부터 초기값 받기 (챗봇 입력)
  onSelect: (item: string) => void; // 🔹 선택된 품목을 부모에 전달
}

const defaultSuggestions = ["사과", "배추", "상추", "양파", "파프리카", "아스파라거스"];

const SearchBar: React.FC<SearchBarProps> = ({ value, onSelect }) => {
  const [query, setQuery] = useState<string>(value); // ✅ 초기값 반영 (챗봇 입력)
  const [suggestions, setSuggestions] = useState(defaultSuggestions);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const searchBarRef = useRef<HTMLDivElement | null>(null);

  // ✅ 부모에서 전달된 `value`가 변경되면 `query` 업데이트
  useEffect(() => {
    setQuery(value);
  }, [value]);

  // 🔹 **외부 클릭 감지해서 리스트 닫기**
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (searchBarRef.current && !searchBarRef.current.contains(event.target as Node)) {
        setShowSuggestions(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // 🔹 **검색어 입력 처리**
  const handleQueryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setQuery(input);
    setShowSuggestions(input.trim() !== ""); // 🔹 입력 중이면 리스트 표시

    if (input.trim() === "") {
      setSuggestions(defaultSuggestions);
    } else {
      const filtered = defaultSuggestions.filter((name) =>
        name.toLowerCase().includes(input.toLowerCase())
      );
      setSuggestions(filtered);
    }
  };

  // 🔹 **품목 선택 시: 입력창 업데이트 및 부모에 전달**
  const handleItemSelect = (item: string) => {
    setQuery(item); // ✅ 선택된 값 입력창 반영
    setShowSuggestions(false);
    onSelect(item);
  };

  // 🔹 **검색 버튼 클릭 시 API 요청 실행**
  const handleSearch = () => {
    if (query.trim() !== "") {
      onSelect(query);
      setShowSuggestions(false);
    }
  };

  return (
    <div ref={searchBarRef} className="bg-white p-4 rounded-lg shadow mb-6 relative">
      <h2 className="text-xl font-semibold mb-4">📌 상품 검색</h2>
      <div className="relative flex items-center">
        <input
          type="text"
          value={query}
          onChange={handleQueryChange}
          onFocus={() => setShowSuggestions(true)}
          placeholder="예: 사과"
          className="w-full border border-gray-300 rounded-lg px-4 py-2"
        />
        <button
          onClick={handleSearch}
          className="absolute right-0 top-1/2 transform -translate-y-1/2 bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
        >
          검색
        </button>
      </div>

      {/* 🔹 **자동완성 리스트 (`ul`) ** */}
      {showSuggestions && suggestions.length > 0 && (
        <ul className="absolute left-0 right-0 bg-white border border-gray-300 rounded-lg mt-2 max-h-40 overflow-y-auto shadow-lg z-50 w-full">
          {suggestions.map((item, index) => (
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
  );
};

export default SearchBar;