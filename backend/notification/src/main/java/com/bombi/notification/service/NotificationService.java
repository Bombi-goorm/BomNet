package com.bombi.notification.service;

import com.bombi.notification.entity.Member;
import com.bombi.notification.entity.PushSubscription;
import com.bombi.notification.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MemberRepository memberRepository;


    public void sendNotification(UUID memberId, String alertMessage) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            // 기본적으로 모든 구독 정보를 브라우저 기반 푸시로 처리
            List<PushSubscription> subscriptions = member.getPushSubscriptions();
            for (PushSubscription sub : subscriptions) {
                // 모든 구독정보에 대해 동일한 웹 푸시 전송 로직 적용
                sendWebPush(sub.getEndpoint(), sub.getP256dh(), sub.getAuth(), alertMessage);
            }
        } else {
            System.out.println("회원 정보를 찾을 수 없습니다. memberId: " + memberId);
        }
    }

    public void sendWebPush(String endpoint, String p256dh, String auth, String message) {
        // 실제 웹 푸시 전송 구현 로직 (예: WebPush 라이브러리 활용)
        System.out.println("웹 푸시 전송: endpoint=" + endpoint + ", message=" + message);
        // 예시: WebPush.send(endpoint, p256dh, auth, message);
    }
}