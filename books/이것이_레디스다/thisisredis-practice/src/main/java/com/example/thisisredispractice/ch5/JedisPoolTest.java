package com.example.thisisredispractice.ch5;


import redis.clients.jedis.*;

import java.util.Map;

public class JedisPoolTest {

    public static void main(String[] args) throws InterruptedException {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setBlockWhenExhausted(true);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);

        Jedis firstClient = pool.getResource();
        firstClient.hset("info:자린고비", "이름", "자린고비");
        firstClient.hset("info:자린고비", "생일", "1970-12-20");

        Jedis secondClient = pool.getResource();
        Map<String, String> result = secondClient.hgetAll("info:자린고비");
        System.out.println("result.get(\"이름\") = " + result.get("이름"));
        System.out.println("result.get(\"생일\") = " + result.get("생일"));

        firstClient.close();
        secondClient.close();
        pool.close();
    }
}
