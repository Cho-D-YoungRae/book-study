package org.example.kafkastudy.chapter3.kafkastreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

public class KStreamJoinKTable {

    private static final String APPLICATION_NAME = "order-join-application";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String ADDRESS_TABLE = "address";
    private static final String ORDER_STREAM = "order";
    private static final String ORDER_JOIN_STREAM = "order_join";

    public static void main(String[] args) {

        final Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        final KTable<String, String> addressTable = builder.table(ADDRESS_TABLE);
        final KTable<String, String> orderStream = builder.table(ORDER_STREAM);

        orderStream.join(
                addressTable,
                (order, address) -> order + " send to " + address
        ).toStream().to(ORDER_JOIN_STREAM);

        final KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
    }
}
