package com.example.kafkastudy.chapter03;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SimpleProducer_2 {

    private static final Logger log = LoggerFactory.getLogger(SimpleProducer_2.class);
    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "localhost:29092,localhost:39092,localhost:49092";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        /*
        producer.send 의 반환 값은 Future 타입이고 get 을 통해서 결과를 동기적으로 가져올 수 있다.
        그러나 동기로 전송 결과를 확인하는 것은 빠른 전송에 허들이 될 수 있다.
        이를 원하지 않는 경우를 위해 프로듀서는 Callback 을 통해서 비동기로 결과를 알 수 있다
         */
        RecordMetadata metadata = producer.send(new ProducerRecord<>(TOPIC_NAME, "testMessage-2-1")).get();
        log.info("sync: {}", metadata);

        /*
        비동기로 결과를 받을 경우 동기로 결과를 받는 경우보다 더 빠른 속도로 데이터를 추가 처리할 수 있지만
        전송하는 데이터의 순서가 중요한 경우 사용하면 안된다.
         */
        producer.send(new ProducerRecord<>(TOPIC_NAME, "testMessage-2-2"), new ProducerCallback());

        producer.flush();
        producer.close();
    }

    static class ProducerCallback implements Callback {

        private static final Logger log = LoggerFactory.getLogger(ProducerCallback.class);

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                log.error("{}", e.getMessage(), e);
            } else {
                log.info("async: {}", recordMetadata);
            }
        }
    }
}
