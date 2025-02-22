import React from "react";

export interface PriceAlertItem {
  name: string;
  variety: string;
  price: string;
}


interface PriceAlertListProps {
  alerts: PriceAlertItem[];
  handleDelete: (index: number) => void;
}

const PriceAlertList: React.FC<PriceAlertListProps> = ({
  alerts,
  handleDelete,
}) => {
  return (
    <div>
      <h2 className="text-2xl font-semibold text-gray-700">가격 알림 설정</h2>
      <div className="bg-white border p-4 rounded-lg shadow-sm">
        <table className="w-full table-auto text-center">
          <thead>
            <tr>
              <th className="py-2 px-4 text-gray-800">품목</th>
              <th className="py-2 px-4 text-gray-800">품종</th>
              <th className="py-2 px-4 text-gray-800">가격</th>
              <th className="py-2 px-4 text-gray-800">삭제</th>
            </tr>
          </thead>
          <tbody>
            {alerts.map((alert, index) => (
              <tr key={index} className="border-t">
                <td className="py-2 px-4 text-gray-800">{alert.name}</td>
                <td className="py-2 px-4 text-gray-800">{alert.variety}</td>
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
    </div>
  );
};

export default PriceAlertList;