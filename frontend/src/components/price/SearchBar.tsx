import { useState } from "react";

interface SearchBarProps {
  onSelect: (item: string) => void; // 선택 시 부모에게 전달할 콜백
}

// 예시 기본 품목 목록
const defaultSuggestions = ["사과", "배추", "상추", "양파", "파프리카", "아스파라거스"];

const SearchBar: React.FC<SearchBarProps> = ({ onSelect }) => {
  const [query, setQuery] = useState("");
  const [suggestions, setSuggestions] = useState(defaultSuggestions);
  const [showSuggestions, setShowSuggestions] = useState(false); // 리스트 표시 상태 추가

  // 검색어 입력 처리
  const handleQueryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setQuery(input);
    setShowSuggestions(input.trim() !== ""); // 입력이 있을 때만 리스트 표시

    if (input.trim() === "") {
      setSuggestions(defaultSuggestions);
    } else {
      const filtered = defaultSuggestions.filter((name) =>
        name.toLowerCase().includes(input.toLowerCase())
      );
      setSuggestions(filtered);
    }
  };

  // 품목 선택 시: 입력창에 반영 + 리스트 닫기
  const handleItemSelect = (item: string) => {
    setQuery(item);
    setShowSuggestions(false); // 리스트 닫기
    onSelect(item);
  };

  // 검색 버튼 클릭 시: 현재 입력값으로 검색 실행 + 리스트 닫기
  const handleSearch = () => {
    if (query.trim() !== "") {
      onSelect(query);
      setQuery("");
      setShowSuggestions(false);
    }
  };

  return (
    <div className="bg-white p-4 rounded-lg shadow mb-6">
      <div className="relative flex items-center">
        <input
          type="text"
          id="item-search"
          value={query}
          onChange={handleQueryChange}
          onFocus={() => setShowSuggestions(true)} // 포커스 시 리스트 표시
          placeholder="예: 사과"
          className="w-full border border-gray-300 rounded-lg px-4 py-2"
        />
        <button
          onClick={handleSearch}
          className="absolute right-2 top-2 bg-blue-500 text-white px-3 py-1 rounded-lg hover:bg-blue-600"
        >
          검색
        </button>
      </div>

      {/* 리스트 팝업창: 검색어가 있고, 리스트가 보일 때만 표시 */}
      {showSuggestions && suggestions.length > 0 && (
        <ul className="absolute bg-white border border-gray-300 rounded-lg mt-2 w-full max-h-40 overflow-y-auto">
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