//package com.bombi.notification.service;
//
//import com.google.cloud.pubsub.v1.Subscriber;
//import com.google.pubsub.v1.ProjectSubscriptionName;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PubSubSubscriber {
//
//    private static final String PROJECT_ID = "axial-theater-453211-j4";
//    private static final String SUBSCRIPTION_ID = "bom-noti-sub";
//
//    public void startSubscriber() {
//        ProjectSubscriptionName subscriptionName =
//                ProjectSubscriptionName.of(PROJECT_ID, SUBSCRIPTION_ID);
//
//        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, (message, consumer) -> {
//            System.out.println("🔹 Received message: " + message.getData().toStringUtf8());
//            consumer.ack(); // 메시지 처리 후 ACK (확인)
//        }).build();
//
//        subscriber.startAsync().awaitRunning();
//        System.out.println("🚀 Listening for messages on " + SUBSCRIPTION_ID);
//    }
//}
//
