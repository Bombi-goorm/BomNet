package com.bombi.notification.service;

import com.bombi.notification.entity.PushSubscription;
import com.bombi.notification.repository.PushSubscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushNotificationListener {

    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final WebPushNotificationService webPushNotificationService;

    @PubSubListener(subscription = "your-subscription-name")
    public void receiveMessage(String messageJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PushNotificationEvent event = objectMapper.readValue(messageJson, PushNotificationEvent.class);

            System.out.println("📩 수신된 알림 이벤트: " + event);

            // 사용자 ID 기반으로 구독 정보 조회 후 알림 전송
            List<PushSubscription> subscriptions = pushSubscriptionRepository.findByMemberId(event.getUserId());

            if (subscriptions.isEmpty()) {
                System.out.println("⚠️ 구독 정보 없음 - 알림 전송 취소: " + event.getUserId());
                return;
            }

            String message = event.getMessage();
            webPushNotificationService.sendPushNotification(subscriptions, message);

        } catch (Exception e) {
            System.err.println("❌ Pub/Sub 메시지 처리 중 오류 발생: " + e.getMessage());
        }
    }
}