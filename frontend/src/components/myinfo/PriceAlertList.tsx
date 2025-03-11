import React from "react";

export interface PriceAlertItem {
  name: string;
  variety: string;
  markets: string;
  price: string;
}

interface PriceAlertListProps {
  alerts: PriceAlertItem[];
  handleDelete: (index: number) => void;
}

const PriceAlertList: React.FC<PriceAlertListProps> = ({ alerts, handleDelete }) => {
  return (
    <div className="bg-white border p-4 rounded-lg shadow-sm">
      <h2 className="text-2xl font-semibold text-gray-700 mb-4">가격 조건 목록</h2>
      <table className="w-full table-auto text-center">
        <thead>
          <tr>
            <th className="py-2 px-4 text-gray-800">품종</th>
            <th className="py-2 px-4 text-gray-800">시장</th>
            <th className="py-2 px-4 text-gray-800">가격</th>
            <th className="py-2 px-4 text-gray-800">삭제</th>
          </tr>
        </thead>
        <tbody>
          {alerts.map((alert, index) => (
            <tr key={index} className="border-t">
              <td className="py-2 px-4 text-gray-800">{alert.variety}</td>
              <td className="py-2 px-4 text-gray-800">{alert.markets}</td>
              <td className="py-2 px-4 text-gray-800">{alert.price}</td>
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
    </div>
  );
};

export default PriceAlertList;