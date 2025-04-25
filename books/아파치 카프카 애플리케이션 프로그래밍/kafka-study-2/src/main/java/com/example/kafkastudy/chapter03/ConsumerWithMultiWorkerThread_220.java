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

public class ConsumerWithMultiWorkerThread_220 {

    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "localhost:29092,localhost:39092,localhost:49092";
    private static final String GROUP_ID = "test-group";

    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs); ExecutorService es = Executors.newCachedThreadPool()) {
            consumer.subscribe(List.of(TOPIC_NAME));
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    ConsumerWorker worker = new ConsumerWorker(consumerRecord.value());
                    es.execute(worker);
                }
            }
        }
    }

    static class ConsumerWorker implements Runnable {

        private static final Logger log = LoggerFactory.getLogger(ConsumerWorker.class);

        private final String recordValue;

        public ConsumerWorker(String recordValue) {
            this.recordValue = recordValue;
        }

        @Override
        public void run() {
            log.info("thread:{}\trecord:{}", Thread.currentThread().getName(), recordValue);
        }
    }
}
