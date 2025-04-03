import React, { useState } from "react";
import { removeNotificationCondition } from "../../api/core_api";
import { PriceAlertCondition } from "../../types/member_types";

interface PriceAlertListProps {
  initialAlerts: PriceAlertCondition[];
}

const PriceAlertList: React.FC<PriceAlertListProps> = ({ initialAlerts }) => {
  const [alerts, setAlerts] = useState<PriceAlertCondition[]>(initialAlerts);
  const [loading, setLoading] = useState<boolean>(false);

  // 삭제 버튼 클릭 시: 해당 알림 id를 이용해 삭제 요청 후 다시 데이터를 가져옵니다.
  const handleDelete = async (index: number) => {
    setLoading(true)
    try {
      const alertId = alerts[index]?.id;
      const data: PriceAlertCondition = {
        notificationConditionId: alertId,
      }
      const deleteResponse = await removeNotificationCondition(data);
      if (deleteResponse.status === "200") {
        setAlerts(deleteResponse.data);
        setLoading(false)
      } else {
        setLoading(false)
        alert('삭제 요청 실패')
      }
    } catch (error) {
      alert('삭제 요청 실패')
      setLoading(false)
    }
  };

  if (loading) {
    return <div className="text-center">🔄 로딩 중...</div>;
  }

  return (
    <div className="bg-white border p-4 rounded-lg shadow-sm">
      <h2 className="text-2xl font-semibold text-gray-700 mb-4">가격 조건 목록</h2>
      {alerts.length === 0 ? (
        <p className="text-gray-500 text-center">알림 조건이 없습니다.</p>
      ) : (
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
              <tr key={alert.id} className="border-t">
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
      )}
    </div>
  );
};

export default PriceAlertList;