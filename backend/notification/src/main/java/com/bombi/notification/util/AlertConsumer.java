package com.bombi.notification.util;

import com.bombi.notification.entity.AlertMessage;
import com.bombi.notification.entity.Member;
import com.bombi.notification.entity.PushSubscription;
import com.bombi.notification.repository.MemberRepository;
import com.bombi.notification.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlertConsumer {

    private final MemberRepository memberRepository;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "alert_queue")
    public void receiveMessage(String message) {
        try {
            // 메시지 파싱: 예) {"userId": "123", "message": "가격 조건 충족!"}
            AlertMessage alertMessage = objectMapper.readValue(message, AlertMessage.class);
            UUID memberId = alertMessage.getUserId();
            String alertContent = alertMessage.getMessage();

            // Member 엔티티 조회 (PushSubscription 정보 포함)
            Member member = memberRepository.findById(memberId).orElse(null);
            if (member != null) {
                List<PushSubscription> subscriptions = member.getPushSubscriptions();
                // 모든 구독정보에 대해 동일한 웹 푸시 알림 전송
                for (PushSubscription sub : subscriptions) {
                    notificationService.sendWebPush(
                            sub.getEndpoint(),
                            sub.getP256dh(),
                            sub.getAuth(),
                            alertContent
                    );
                }
            } else {
                System.err.println("회원 정보를 찾을 수 없습니다. memberId: " + memberId);
            }
        } catch (Exception e) {
            System.err.println("메시지 처리 실패: " + e.getMessage());
            // 재시도 또는 별도 처리 로직 추가 가능
        }
    }
}