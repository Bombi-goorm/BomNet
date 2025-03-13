package com.bombi.notification.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MessageFormatter {

    public static String formatMessage(String jsonMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonMessage);

            String product = root.path("product").asText();
            String targetPrice = root.path("targetPrice").asText();
            String reachedPrice = root.path("reachedPrice").asText();
            String market = root.path("market").asText();


            return String.format("%s의 가격이 %s에서 %s원에 도달했습니다. (기준가 : %s원)",
                    product, market, reachedPrice, targetPrice);
        } catch (Exception e) {
            log.error("알림 메시지를 생성하는 중 오류가 발생했습니다.");
            return "알림 메시지를 생성하는 중 오류가 발생했습니다.";
        }
    }
}