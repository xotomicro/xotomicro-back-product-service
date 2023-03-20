package com.boilerplate.xotomicro_back_product_service.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFuture;

@ExtendWith(SpringExtension.class)
public class KafkaSenderTest {
    private final String topic = "TOPIC";
    private final String message = "MESSAGE";

    @MockBean
    private KafkaTemplate template;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaSender kafkaSender;

    @BeforeEach
    void setUp() {
        ListenableFuture<SendResult<String, String>> future = template.send(topic, message);
        when(kafkaTemplate.send(topic, message)).thenReturn(future);
    }

    @DisplayName("Kafka Exception")
    @Test
    void exceptionWhenSend() {
        kafkaSender.send("topicA", "messageB");
    }

    // TODO: fix unit test
    @DisplayName("Send")
    @Test
    void send() {
        ListenableFuture<SendResult<String, String>> future = template.send(topic, message);
        when(kafkaTemplate.send(topic, message)).thenReturn(future);
        if (future != null) {
            kafkaSender.send(topic, message);
        }
    }
}
