package com.example.kafkastudy.chapter03;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiConsumerThread_223 {

    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "localhost:29092,localhost:39092,localhost:49092";
    private static final String GROUP_ID = "test-group";
    private static final int CONSUMER_THREAD_COUNT = 3;

    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try (ExecutorService es = Executors.newFixedThreadPool(CONSUMER_THREAD_COUNT)) {
            for (int i = 0; i < CONSUMER_THREAD_COUNT; i++) {
                es.execute(new ConsumerWorker(configs, TOPIC_NAME));
            }
        }
    }

    static class ConsumerWorker implements Runnable {

        private static final Logger log = LoggerFactory.getLogger(ConsumerWorker.class);

        private final Properties configs;
        private final String topic;

        public ConsumerWorker(Properties configs, String topic) {
            this.configs = configs;
            this.topic = topic;
        }

        @Override
        public void run() {
            // KafkaConsumer 는 스레드 세이프하지 않아서 스레드별로 인스턴스를 별개로 ㅅ만들어서 운영해야만 한다.
            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs)) {
                consumer.subscribe(List.of(topic));
                while (true) {
                    ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                        log.info("thread:{}\trecord:{}", Thread.currentThread().getName(), consumerRecord);
                    }
                }
            }
        }
    }
}
