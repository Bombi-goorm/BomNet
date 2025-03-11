package com.bombi.notification.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;

public class MessageFormatter {

    public static String formatMessage(String jsonMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonMessage);

            String product = root.path("product").asText();
            String priceRaw = root.path("price").asText();
            List<String> markets = objectMapper.convertValue(root.path("markets"), List.class);

            String marketList = String.join(", ", markets); // "부산엄궁, 서울강서"

            // 🔹 가격 변환 로직 (숫자만 남기기)
            String price = priceRaw.replace("+inf", "").replace("-inf", "").trim();

            return String.format("%s의 가격이 %s에서 %s원에 도달했습니다.",
                    product, marketList, price);
        } catch (Exception e) {
            e.printStackTrace();
            return "알림 메시지를 생성하는 중 오류가 발생했습니다.";
        }
    }
}