package com.example.demo.pulsar;

import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class ProducerDemo {
    private static final Producer<byte[]> PULSAR_PRODUCER;
    private static final PulsarClient CLIENT;
    private static final Consumer<byte[]> PULSAR_CONSUMER;
    static {
        PulsarClient client;
        Producer<byte[]> producer;
        Consumer<byte[]> consumer;
        try {
            client = PulsarClient.builder()
                    .serviceUrl("pulsar://127.0.0.1:6650")
                    .build();
            producer =
                    client.newProducer()
                    .topic("my-topic")
                    .create();
            consumer = client.newConsumer()
                    .topic("my-topic")
                    .subscriptionName("my-subscription")
                    .subscribe();
        } catch (PulsarClientException e) {
            producer = null;
            client = null;
            consumer = null;
            e.printStackTrace();
        }
        PULSAR_PRODUCER = producer;
        CLIENT = client;
        PULSAR_CONSUMER = consumer;
    }

    @PostConstruct
    public void init() {
        (new Thread(() -> {
            System.out.println("start Message received");
            while (true) {
                try {
                    // Wait for a message
                    Message<byte[]> msg = PULSAR_CONSUMER.receive();
                    try {
                        // Do something with the message
                        System.out.println("Message received: " + new String(msg.getData()));

                        // Acknowledge the message so that it can be deleted by the message broker
                        PULSAR_CONSUMER.acknowledge(msg);
                    } catch (Exception e) {
                        System.out.println("Message received: " + new String(msg.getData()));
                        // Message failed to process, redeliver later
                        PULSAR_CONSUMER.negativeAcknowledge(msg);
                    }
                } catch (Exception e) {
                    System.out.println("Message received: " + e.getMessage());
                }
            }
        })).start();
    }

    public static String sendMessage(String msg) {
        try {
            return PULSAR_PRODUCER.send(msg.getBytes(StandardCharsets.UTF_8)).toString();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\r\n" + Arrays.toString(e.getStackTrace()));
            return e.getMessage();
        }
    }
}
