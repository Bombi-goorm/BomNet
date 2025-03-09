package com.bombi.notification.service;

import com.bombi.notification.entity.NotificationType;
import com.bombi.notification.entity.PushSubscription;
import com.bombi.notification.repository.PushSubscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WebPushNotificationService {
    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final NotificationService notificationService;
    private PushService pushService;


    @Value("${vapid.public}")
    private String vapidPublicKey;

    @Value("${vapid.private}")
    private String vapidPrivateKey;

    @Value("${vapid.subject}")
    private String vapidSubject;

    private String title = "test";
    private String message = "test111";

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }


    // pushService가 null이면 초기화하는 메서드 (lazy initialization)
    private void ensurePushServiceInitialized() {
        if (this.pushService == null) {
            try {
                this.pushService = new PushService(vapidPublicKey, vapidPrivateKey, vapidSubject);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to initialize PushService", ex);
            }
        }
    }

    public void sendNotification() {
        // pushService가 아직 null이면 초기화
        ensurePushServiceInitialized();

        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByMemberId(UUID.fromString("3e9632a1-0f54-4703-8803-fe90e715cda9"));

        for (PushSubscription sub : subscriptions) {
            try {
                // 사용자 푸시 구독 정보
                String endpoint = sub.getEndpoint();

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> map = new HashMap<>();
                map.put(title, message);
                String payload = objectMapper.writeValueAsString(map);



                // 푸시 알림 객체 생성
                Notification notification = new Notification(
                        sub.getEndpoint(),
                        sub.getP256dh(),
                        sub.getAuth(),
                        payload
                );

                // 푸시 전송
                pushService.send(notification);
                notificationService.createAndSaveNotification(
                        sub.getMember(),
                        NotificationType.TARGET_PRICE,
                        title,
                        message
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
