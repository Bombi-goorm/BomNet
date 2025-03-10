self.addEventListener("push", (event) => {
  if (!event.data) return;

  const data = event.data.json();
  console.log("📩 [Service Worker] 푸시 알림 수신:", data);

  const options = {
      body: data.message || "새로운 알림이 도착했습니다.",
      icon: data.icon || "/bomnetlogo.png",  // 🔥 작은 아이콘 변경
      image: data.image || null, // 🔥 크롬에서 큰 이미지 추가 (선택 사항)
      badge: data.badge || "/bomnetlogo.png", // 🔥 안드로이드 배지 아이콘
      data: { url: data.url || "/" },
  };

  event.waitUntil(self.registration.showNotification(data.title, options));
});

// 알림 클릭 이벤트 핸들러
self.addEventListener("notificationclick", (event) => {
    event.notification.close(); // 🔹 클릭 후 알림 닫기

    event.waitUntil(
        clients.matchAll({ type: "window", includeUncontrolled: true }).then((clientList) => {
            for (const client of clientList) {
                if (client.url.includes("/alarm") && "focus" in client) {
                    return client.focus(); // 🔹 이미 열려있으면 포커스
                }
            }
            if (clients.openWindow) {
                return clients.openWindow("/alarm"); // 🔹 새 창으로 `/alarm` 페이지 열기
            }
        })
    );
});
