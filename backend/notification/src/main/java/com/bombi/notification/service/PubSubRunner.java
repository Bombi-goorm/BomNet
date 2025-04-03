package com.bombi.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PubSubRunner implements CommandLineRunner {

    private static final int MAX_RETRIES = 10;
    private static final long RETRY_DELAY_MS = 30000L;

    private final PubSubSubscriber subscriber;

    public PubSubRunner(PubSubSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void run(String... args) {
        log.info("Pub/Sub 구독 시도...");
        int attempt = 0;

        while (attempt < MAX_RETRIES) {
            try {
                subscriber.startSubscribers();
                log.info("Pub/Sub 구독 완료");
                return;  // 성공하면 종료
            } catch (Exception e) {
                attempt++;
                log.error("Pub/Sub 구독 실패 (시도 {}회)", attempt, e);

                if (attempt >= MAX_RETRIES) {
                    log.error("Pub/Sub 구독 재시도 {}회 초과 - 구독 실패", MAX_RETRIES);
                    break;
                }

                log.warn("Pub/Sub 구독 재시도 예정 - {}초 후 (시도 {} / {})",
                        RETRY_DELAY_MS / 1000, attempt + 1, MAX_RETRIES);
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    log.error("재시도 대기 중 인터럽트 발생", ie);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}

