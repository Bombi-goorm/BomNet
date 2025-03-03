// 푸시 알림 수신 이벤트
self.addEventListener("push", (event) => {
  // 푸시 데이터가 있는 경우 파싱
  const data = event.data ? event.data.json() : {};
  const title = data.title || "푸시 알림";
  const options = {
    body: data.body || "새로운 알림이 도착했습니다.",
    icon: data.icon || "/icon.png",  // 기본 아이콘 경로 설정
    data: {
      url: data.url || "/"  // 알림 클릭 시 이동할 URL
    }
  };

  event.waitUntil(self.registration.showNotification(title, options));
});

// 알림 클릭 이벤트 핸들러
self.addEventListener("notificationclick", (event) => {
  event.notification.close();

  event.waitUntil(
    clients.matchAll({ type: "window" }).then((clientList) => {
      // 이미 열린 창이 있으면 해당 창으로 이동
      for (const client of clientList) {
        if (client.url === event.notification.data.url && "focus" in client) {
          return client.focus();
        }
      }
      // 없으면 새 창 열기
      if (clients.openWindow) {
        return clients.openWindow(event.notification.data.url);
      }
    })
  );
});
