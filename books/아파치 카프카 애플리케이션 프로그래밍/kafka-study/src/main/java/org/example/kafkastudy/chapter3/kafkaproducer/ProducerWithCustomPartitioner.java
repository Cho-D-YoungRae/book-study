package org.example.kafkastudy.chapter3.kafkaproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.kafkastudy.chapter3.kafkaproducer.module.CustomPartitioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithCustomPartitioner {

    private static final Logger logger = LoggerFactory.getLogger(ProducerWithCustomPartitioner.class);
    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {

        final Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);

        final KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        final ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, "Pangyo", "Pangyo");
        producer.send(producerRecord);

        logger.info("{}", producerRecord);
        producer.flush();
        producer.close();
    }
}