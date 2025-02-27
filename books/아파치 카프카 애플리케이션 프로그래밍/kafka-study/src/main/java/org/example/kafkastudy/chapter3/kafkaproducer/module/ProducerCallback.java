package org.example.kafkastudy.chapter3.kafkaproducer.module;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerCallback implements Callback {

    private final static Logger logger = LoggerFactory.getLogger(ProducerCallback.class);

    @Override
    public void onCompletion(final RecordMetadata recordMetadata, final Exception e) {
        if (e != null) {
            logger.error(e.getMessage(), e);
        } else {
            logger.info(recordMetadata.toString());
        }
    }
}
