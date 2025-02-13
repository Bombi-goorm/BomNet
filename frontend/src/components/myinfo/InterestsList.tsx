import React from "react";

export interface InterestItem {
  name: string;
  variety: string;
  price: string;
  isSelected: boolean;
}

interface InterestsListProps {
  items: InterestItem[];
  handleCheck: (index: number) => void;
  handleDelete: (index: number) => void;
}

const InterestsVarietiesSettings: React.FC<InterestsListProps> = ({
  items,
  handleCheck,
  handleDelete,
}) => {
  return (
    <div>
      <h2 className="text-2xl font-semibold text-gray-700">
        관심 품종 설정
      </h2>
      <div className="bg-white border p-4 rounded-lg shadow-sm">
        <table className="w-full table-auto text-center">
          <thead>
            <tr>
              <th className="py-2 px-4 text-gray-800">품목</th>
              <th className="py-2 px-4 text-gray-800">품종</th>
              <th className="py-2 px-4 text-gray-800">가격</th>
              <th className="py-2 px-4 text-gray-800">대표 품목 선택</th>
              <th className="py-2 px-4 text-gray-800">삭제</th>
            </tr>
          </thead>
          <tbody>
            {items.map((item, index) => (
              <tr key={index} className="border-t">
                <td className="py-2 px-4 text-gray-800">{item.name}</td>
                <td className="py-2 px-4 text-gray-800">{item.variety}</td>
                <td className="py-2 px-4 text-gray-800">{item.price}</td>
                <td className="py-2 px-4">
                  <input
                    type="checkbox"
                    checked={item.isSelected}
                    onChange={() => handleCheck(index)}
                    className="form-checkbox mr-2"
                  />
                  선택
                </td>
                <td className="py-2 px-4">
                  <button
                    onClick={() => handleDelete(index)}
                    className="text-red-500 hover:text-red-700"
                  >
                    삭제
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <p className="text-sm text-gray-500 mt-2 text-center">
          전체 리스트에서 최대 5개까지 대표 품목으로 선택할 수 있습니다.
        </p>
      </div>
    </div>
  );
};

export default InterestsVarietiesSettings;
