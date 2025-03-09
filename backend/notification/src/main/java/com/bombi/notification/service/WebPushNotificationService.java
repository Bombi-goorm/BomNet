package com.bombi.notification.service;

import com.bombi.notification.entity.Member;
import com.bombi.notification.entity.PushSubscription;
import com.bombi.notification.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebPushNotificationService {

    private final PushService pushService;

    public void sendPushNotification(List<PushSubscription> subscriptions, String message) {
        for (PushSubscription subscription : subscriptions) {
            try {
                Subscription sub = new Subscription(
                        subscription.getEndpoint(),
                        new Subscription.Keys(subscription.getP256dh(), subscription.getAuth())
                );

                Notification notification = new Notification(
                        sub,
                        message.getBytes(StandardCharsets.UTF_8)
                );

                pushService.send(notification);
                System.out.println("✅ 푸시 알림 전송 완료: " + subscription.getEndpoint());

            } catch (Exception e) {
                System.err.println("❌ 푸시 알림 전송 실패: " + e.getMessage());
            }
        }
    }
}