package com.example.thisisredispractice.ch5;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 초당 처리건수 1787.6008
 * 소요시간 5.5940902E8초
 */
public class RedisInsertTest {

    public static final float TOTAL_OP = 1_000_000f;

    public static void main(String[] args) {
        JedisPool pool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = pool.getResource();
        long start = now();
        long loopTime = now();

        for (int i = 1; i <= TOTAL_OP; i++) {
            String keyVal = String.valueOf("key" + (100_000_000 + i));
            jedis.set(keyVal, keyVal);
        }

        long elapsed = now() - start;
        System.out.println("초당 처리건수 " + (TOTAL_OP / elapsed * 1000f));
        System.out.println("소요시간 " + (elapsed * 1000f) + "초");
        jedis.close();
    }

    private static long now() {
        return System.currentTimeMillis();
    }
}
