package com.example.kafkastudy.chapter03;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducer_1 {

    private static final Logger log = LoggerFactory.getLogger(SimpleProducer_1.class);
    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "localhost:29092,localhost:39092,localhost:49092";

    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        String messageValue = "testMessage";
        // 메시지 키는 따로 선언하지 않았으므로 null
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, messageValue);
        // 즉각적인 전송을 뜻하는 것이 아니라, 파라미터로 들어간 record 를 프로듀서 내부에 가지고 있다가 배치 형태로 묶어서 브로커에 전송
        // 이 메서드 호출하면 파티셔너에 의해 레코드가 토픽의 어느 파티션으로 전송될 것인지 정해짐
        producer.send(producerRecord);
        log.info("Message sent: {}", producerRecord);

        // 프로듀서 내부 버퍼에 가지고 있던 레코드 배치를 브로커로 전송
        producer.flush();
        // producer 인스턴스의 리소스들을 안전하게 종료
        producer.close();
    }
}
