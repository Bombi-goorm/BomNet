package com.bombi.notification.service;

import com.bombi.notification.entity.NotificationType;
import com.bombi.notification.entity.PushSubscription;
import com.bombi.notification.repository.PushSubscriptionRepository;
import com.bombi.notification.util.MessageFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebPushNotificationService {

    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final NotificationService notificationService;
    private PushService pushService;

    @Value("${vapid.public}")
    private String vapidPublicKey;

    @Value("${vapid.private}")
    private String vapidPrivateKey;

    private String vapidSubject = "mailto:your-email@example.com";

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private void ensurePushServiceInitialized() {
        if (this.pushService == null) {
            try {
                this.pushService = new PushService(vapidPublicKey, vapidPrivateKey, vapidSubject);
            } catch (Exception ex) {
                log.error("PushService 초기화 실패", ex);
                throw new RuntimeException("Failed to initialize PushService", ex);
            }
        }
    }

    public void sendBatchNotifications(List<String> messages, String type) {
        ensurePushServiceInitialized();
        String serverUrl = "http://localhost:8183";

        List<PushSubscription> subscriptions = pushSubscriptionRepository.findAll();
        List<com.bombi.notification.entity.Notification> notificationEntities = new ArrayList<>();



        for (String message : messages) {
            // 알림 내용 자연어로 변경
            String formattedMessage = "";
            try {
                if ("PRICE".equalsIgnoreCase(type)) {
                    formattedMessage = MessageFormatter.formatPriceMessage(message);
                } else if ("WEATHER".equalsIgnoreCase(type)) {
                    formattedMessage = MessageFormatter.formatWrnMessage(message);
                }
            } catch (Exception e) {
                log.error("메시지 포맷 실패 - type={}, message={}", type, message, e);
                continue;  // 해당 메시지 스킵
            }

            for (PushSubscription sub : subscriptions) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, String> map = new HashMap<>();
                    map.put("title", getTitleByType(type));
                    map.put("message", formattedMessage);
                    map.put("icon", serverUrl + "/images/bomnetlogo.png"); // 작은 아이콘
                    map.put("image", serverUrl + "/images/bomnetlogo.jpg"); // 큰 이미지
                    map.put("badge", serverUrl + "/images/bomnetlogo.png"); // 배지 아이콘
                    map.put("url", "http://localhost:5173/alarm");
                    String payload = objectMapper.writeValueAsString(map);

                    Notification notification = new Notification(
                            sub.getEndpoint(),
                            sub.getP256dh(),
                            sub.getAuth(),
                            payload
                    );
                    pushService.send(notification);

                    notificationEntities.add(
                            com.bombi.notification.entity.Notification.builder()
                                    .member(sub.getMember())
                                    .notificationType(getNotificationTypeByType(type))
                                    .title(getTitleByType(type))
                                    .message(formattedMessage)
                                    .isRead("N")
                                    .build()
                    );

                } catch (Exception e) {
                    log.error("푸시 알림 전송 실패 - endpoint={}, memberId={}, type={}",
                            sub.getEndpoint(), sub.getMember().getId(), type, e);
                }
            }
        }

        // 🔹 알림객체 배치 저장
        try {
            notificationService.notificationBatchSave(notificationEntities);
        } catch (Exception e) {
            log.error("알림 DB 저장 실패 - count={}, type={}", notificationEntities.size(), type, e);
        }
    }

    private String getTitleByType(String type) {
        return "PRICE".equals(type) ? "가격 알림" : "기상 알림";
    }

    private NotificationType getNotificationTypeByType(String type) {
        return "PRICE".equals(type) ? NotificationType.TARGET_PRICE : NotificationType.WEATHER;
    }
}