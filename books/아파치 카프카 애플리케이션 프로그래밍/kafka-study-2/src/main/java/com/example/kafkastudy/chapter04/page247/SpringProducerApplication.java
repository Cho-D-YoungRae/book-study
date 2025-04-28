package com.example.kafkastudy.chapter04.page247;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SpringProducerApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringProducerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringProducerApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(KafkaTemplate<Integer, String> kafkaTemplate) {
        String topicName = "test";
        return args -> {
            for (int i = 0; i < 10; i++) {
                String message = "test-" + i;
                log.info("Sending message: {}", message);
                kafkaTemplate.send(topicName, message);
            }
            System.exit(0);
        };
    }
}
