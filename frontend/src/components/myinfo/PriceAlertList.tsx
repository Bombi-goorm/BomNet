import React, { useState, useEffect } from "react";
import { getNotifications, removeNotificationCondition } from "../../api/core_api";
import { NotificationCondition } from "../../types/member_types";

const PriceAlertList: React.FC = () => {
  const [alerts, setAlerts] = useState<NotificationCondition[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  // 알림 데이터를 가져오는 함수
  const fetchAlerts = async () => {
    try {
      const response = await getNotifications();
      if (response.status === "200" && response.data.notifications) {
        setAlerts(response.data.conditions);
      } else {
        console.error("알림 데이터를 가져올 수 없습니다.");
      }
    } catch (error) {
      console.error("Error fetching notifications:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAlerts();
  }, []);

  // 삭제 버튼 클릭 시: 해당 알림 id를 이용해 삭제 요청 후 다시 데이터를 가져옵니다.
  const handleDelete = async (index: number) => {
    try {
      const alertId = alerts[index].id;
      const deleteResponse = await removeNotificationCondition(alertId);
      if (deleteResponse.status === "200") {
        // 삭제 성공 시, 업데이트된 알림 목록을 다시 가져옴
        await fetchAlerts();
      } else {
        console.error("삭제 실패:", deleteResponse.message);
      }
    } catch (error) {
      console.error("삭제 요청 에러:", error);
    }
  };

  if (loading) {
    return <div className="text-center">로딩 중...</div>;
  }

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
    </div>
  );
};

export default PriceAlertList;