package com.bombi.notification.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PubSubSubscriber {

    @Value("${gcp.projectId}")
    private String PROJECT_ID;

    @Value("${gcp.sub.price}")
    private String PRICE_SUBSCRIPTION_ID;

    @Value("${gcp.sub.weather}")
    private String WEATHER_SUBSCRIPTION_ID;

    @Value("${gcp.pubsub.credentials}")
    private String credentialJson;


    // 개별 큐 (가격 & 기상)
    private final BlockingQueue<String> priceQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> weatherQueue = new LinkedBlockingQueue<>();

    private final WebPushNotificationService webPushNotificationService;

    // **CPU 코어 기반 최적 스레드풀**
    private final ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2,  // 최소 스레드
            Runtime.getRuntime().availableProcessors() * 4,  // 최대 스레드
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), // 큐 크기 1000
            new ThreadPoolExecutor.CallerRunsPolicy() // 누락 없이 모든 메시지 처리
    );

    @PostConstruct
    public void init() {
        startSubscribers();  // ✅ 구독 시작을 애플리케이션 초기화 시 자동 호출
    }

    /**
     * 🔹 다중 토픽 구독 (가격 & 기상 알림)
     */
    public void startSubscribers() {

        try {
            // ✅ JSON → Credentials 변환
            ByteArrayInputStream stream = new ByteArrayInputStream(credentialJson.getBytes(StandardCharsets.UTF_8));
            ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(stream);

            FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);

            // ✅ credentialsProvider 전달하도록 수정
            startSubscriber(PRICE_SUBSCRIPTION_ID, priceQueue, credentialsProvider);
            startSubscriber(WEATHER_SUBSCRIPTION_ID, weatherQueue, credentialsProvider);

            Executors.newSingleThreadExecutor().submit(() -> processBatches(priceQueue, "PRICE"));
            Executors.newSingleThreadExecutor().submit(() -> processBatches(weatherQueue, "WEATHER"));

        } catch (Exception e) {
            log.error("❗ Pub/Sub 구독자 초기화 실패", e);
        }
    }

    private void startSubscriber(String subscriptionId, BlockingQueue<String> queue, FixedCredentialsProvider credentialsProvider) {
        try {
            ProjectSubscriptionName subscriptionName =
                    ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);

            Subscriber subscriber = Subscriber.newBuilder(subscriptionName, (message, consumer) -> {
                        String pubSubMessage = message.getData().toStringUtf8();
                        try {
                            queue.offer(pubSubMessage);
                            consumer.ack();
                        } catch (Exception e) {
                            log.error("메시지 큐 적재 실패 - subscriptionId={}, message={}", subscriptionId, pubSubMessage, e);
                            consumer.nack();
                        }
                    }).setCredentialsProvider(credentialsProvider)
                    .build();

            subscriber.startAsync().awaitRunning();
            log.info("🚀 Listening for messages on " + subscriptionId);
        } catch (Exception e) {
            log.error("개별 구독자 실행 실패 - subscriptionId={}", subscriptionId, e);
            throw new RuntimeException("구독 시작 실패 - subscriptionId=" + subscriptionId, e);
        }
    }

    private void processBatches(BlockingQueue<String> queue, String type) {
        while (true) {
            try {
                List<String> batch = new ArrayList<>();
                queue.drainTo(batch, 100); // 최대 100개씩 배치 처리

                if (!batch.isEmpty()) {
                    executorService.submit(() -> {
                        try {
                            webPushNotificationService.sendBatchNotifications(batch, type);
                        } catch (Exception e) {
                            log.error("배치 알림 전송 실패 - type={}, batchSize={}", type, batch.size(), e);
                        }
                    });
                }

                Thread.sleep(100); // CPU 부하 방지
            } catch (InterruptedException e) {
                log.warn("배치 처리 스레드 인터럽트 - type={}", type);
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("배치 처리 중 예외 발생 - type={}", type, e);
            }
        }
    }
}