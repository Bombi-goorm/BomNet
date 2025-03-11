import React, { useState, useEffect } from "react";
import { Notification } from "../types/member_types";
import { getNotifications, readAllNotifications } from "../api/core_api";
import Header from "../components/Header";

const NotificationList: React.FC = () => {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  // 알림 데이터를 가져오는 함수
  const fetchNotifications = async () => {
    try {
      const response = await getNotifications();
      if (response.status === "200" && response.data.notifications) {
        setNotifications(response.data.notifications);
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
    fetchNotifications();
  }, []);

  // 전체 읽음 처리 함수
  const handleReadAll = async () => {
    try {
      const response = await readAllNotifications();
      if (response.status === "200") {
        // API 호출 성공 시, 모든 알림의 isRead 값을 "T"로 업데이트
        setNotifications((prev) =>
          prev.map((notif) => ({ ...notif, isRead: "T" }))
        );
      } else {
        console.error("전체 읽음 처리 실패:", response.message);
      }
    } catch (error) {
      console.error("전체 읽음 처리 에러:", error);
    }
  };

  // 알림 토글: 클릭 시 isRead 값을 "T"와 "F"로 전환
  const handleToggle = (index: number) => {
    setNotifications((prev) =>
      prev.map((notif, i) =>
        i === index
          ? { ...notif, isRead: notif.isRead === "T" ? "F" : "T" }
          : notif
      )
    );
  };

  // 알림 삭제 핸들러
  const handleDelete = (index: number) => {
    setNotifications((prev) => prev.filter((_, i) => i !== index));
  };

  if (loading) {
    return <div className="text-center">로딩 중...</div>;
  }

  return (
    <>
       <Header />
       <div className="bg-white border p-4 rounded-lg shadow-sm">
        
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-2xl font-semibold text-gray-700">알림 목록</h2>
          <button
            onClick={handleReadAll}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
          >
            전체 읽음 처리
          </button>
        </div>
        <ul>
          {notifications.map((notification, index) => (
            <li
              key={notification.id}
              className="flex justify-between items-center border-b py-2"
            >
              <div
                className="cursor-pointer"
                onClick={() => handleToggle(index)}
              >
                <strong>{notification.title}</strong>{" "}
                <span>
                  {notification.isRead === "T" ? "읽음" : "읽지 않음"}
                </span>
                <p className="text-sm text-gray-600">{notification.content}</p>
              </div>
              <button
                onClick={() => handleDelete(index)}
                className="text-red-500 hover:text-red-700"
              >
                삭제
              </button>
            </li>
          ))}
        </ul>
      </div>
    
    </>
   
  );
};

export default NotificationList;