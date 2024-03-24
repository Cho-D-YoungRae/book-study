package org.example.kafkastudy.chapter3.kafkaproducer.module;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

public class CustomPartitioner implements Partitioner {

    @Override
    public int partition(
            final String topic,
            final Object key,
            final byte[] keyBytes,
            final Object value,
            final byte[] valueBytes,
            final Cluster cluster
    ) {
        if (keyBytes == null) {
            throw new IllegalArgumentException("Key must be provided");
        }
        if (((String) key).equals("Pangyo")) {
            return 0;
        }

        final List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        final int numPartitions = partitions.size();
        return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(final Map<String, ?> map) {

    }
}
