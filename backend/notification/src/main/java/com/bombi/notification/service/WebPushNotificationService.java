package com.bombi.notification.service;

import com.bombi.notification.entity.NotificationType;
import com.bombi.notification.entity.PushSubscription;
import com.bombi.notification.repository.PushSubscriptionRepository;
import com.bombi.notification.util.MessageFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
            // 주제에 따라 다른 포매터 적용
            if ("PRICE".equalsIgnoreCase(type)) {
                formattedMessage = MessageFormatter.formatPriceMessage(message);
            } else if ("WEATHER".equalsIgnoreCase(type)) {
                formattedMessage = MessageFormatter.formatWrnMessage(message);
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
                    e.printStackTrace();
                }
            }
        }

        // 🔹 알림객체 배치 저장
        notificationService.notificationBatchSave(notificationEntities);
    }

    private String getTitleByType(String type) {
        if (type.equals("PRICE")) {
            return "가격 알림";
        }else {
            return "기상 알림";
        }
    }

    private NotificationType getNotificationTypeByType(String type) {
        if(type.equals("PRICE")){
            return NotificationType.TARGET_PRICE;
        }else{
            return NotificationType.WEATHER;
        }
    }
}