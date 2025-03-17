package com.bombi.notification.service;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PubSubSubscriber {

    @Value("${gcp.projectId}")
    private static String PROJECT_ID;

    @Value("${gcp.sub.price}")
    private static String PRICE_SUBSCRIPTION_ID;

    @Value("${gcp.sub.weather}")
    private static String WEATHER_SUBSCRIPTION_ID;

//    private static final String PROJECT_ID = "goorm-bomnet";

    // 🔹 프로덕션 토픽 (가격 & 기상 알림)
//    private static final String PRICE_SUBSCRIPTION_ID = "bomnet-test-sub";
//    private static final String WEATHER_SUBSCRIPTION_ID = "bomnet-wrn-topic-sub";

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

    /**
     * 🔹 다중 토픽 구독 (가격 & 기상 알림)
     */
    public void startSubscribers() {
        startSubscriber(PRICE_SUBSCRIPTION_ID, priceQueue);
        startSubscriber(WEATHER_SUBSCRIPTION_ID, weatherQueue);

        Executors.newSingleThreadExecutor().submit(() -> processBatches(priceQueue, "PRICE"));
        Executors.newSingleThreadExecutor().submit(() -> processBatches(weatherQueue, "WEATHER"));
    }

    private void startSubscriber(String subscriptionId, BlockingQueue<String> queue) {
        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);

        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, (message, consumer) -> {
            String pubSubMessage = message.getData().toStringUtf8();
//            System.out.println("🔹 Received message [" + subscriptionId + "]: " + pubSubMessage + "::" + LocalDateTime.now());
            queue.offer(pubSubMessage);
            consumer.ack();
        }).build();

        subscriber.startAsync().awaitRunning();
        log.info("🚀 Listening for messages on " + subscriptionId);
//        System.out.println("🚀 Listening for messages on " + subscriptionId);
    }

    private void processBatches(BlockingQueue<String> queue, String type) {
        while (true) {
            try {
                List<String> batch = new ArrayList<>();
                queue.drainTo(batch, 100); // 최대 100개씩 배치 처리

                if (!batch.isEmpty()) {
                    executorService.submit(() -> webPushNotificationService.sendBatchNotifications(batch, type));
                }

                Thread.sleep(100); // CPU 부하 방지
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}