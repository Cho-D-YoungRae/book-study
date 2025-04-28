package com.example.kafkastudy.chapter04.page258;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;

@SpringBootApplication
public class SpringConsumerApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringConsumerApplication.class, args);
    }

    /**
     * - 가장 기본적인 리스너 선언
     */
    @KafkaListener(topics = "test", groupId = "test-group-00")
    void recordListener(ConsumerRecord<String, String> message) {
        log.info("00 message: {}", message);
    }

    /**
     * - 메시지 값을 파라미터로 받는 리스너
     */
    @KafkaListener(topics = "test", groupId = "test-group-01")
    void singleTopicListener(String message) {
        log.info("01 message: {}", message);
    }

    /**
     * 개별 리스너에 카프카 컨슈머 옵션값을 부여
     */
    @KafkaListener(
            topics = "test",
            groupId = "test-group-02",
            properties = {
                    "max.poll.interval.ms=60000",
                    "auto.ofset.reset=earliest",
            }
    )
    void singleTopicWithPropertiesListener(ConsumerRecord<String, String> message) {
        log.info("02 message: {}", message);
    }

    /**
     * - concurrency 만큼 카프카 컨슈머 스레드를 실행
     */
    @KafkaListener(
            topics = "test",
            groupId = "test-group-03",
            concurrency = "3"
    )
    void concurrentTopicListener(ConsumerRecord<String, String> message) {
        log.info("03 message: {}", message);
    }

    /**
     * - 특정 토픽의 특정 파티션만 구독
     */
    @KafkaListener(
            groupId = "test-group-04",
            topicPartitions = {
                    @TopicPartition(topic = "test01", partitions = {"0", "1"}),
                    @TopicPartition(topic = "test02", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "3"))
            })
    void listenSpecificPartition(ConsumerRecord<String, String> message) {
        log.info("04 message: {}", message);
    }
}
