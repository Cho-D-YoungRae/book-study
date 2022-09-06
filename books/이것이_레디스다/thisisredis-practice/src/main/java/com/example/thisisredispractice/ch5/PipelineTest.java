package com.example.thisisredispractice.ch5;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 초당 처리건수 1787.6008
 * 소요시간 5.5940902E8초
 */
public class PipelineTest {

    public static final int TOTAL_OPERATIONS = 1_000_000;

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.connect();
        long start = System.currentTimeMillis();

        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < TOTAL_OPERATIONS; i++) {
            String key = "key" + (100_000_000 + i);
            String val = "val" + (100_000_000 + i);
            jedis.set(key, val);
        }
        pipeline.sync();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("초당 처리건수 " + ((double) TOTAL_OPERATIONS / elapsed * 1000f));
        System.out.println("소요시간 " + (elapsed * 1000f) + "초");
        jedis.close();
    }
}
