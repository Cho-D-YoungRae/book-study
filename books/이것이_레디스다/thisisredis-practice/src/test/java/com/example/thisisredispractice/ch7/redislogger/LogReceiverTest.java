package com.example.thisisredispractice.ch7.redislogger;

import com.example.thisisredispractice.JedisHelper;
import com.example.thisisredispractice.ch7.redislogger.LogReceiver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LogReceiverTest {

    private static JedisHelper jedisHelper;

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        jedisHelper = JedisHelper.getInstance();
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        jedisHelper.destroyPool();
    }

    @Test
    void testLogger() {
        LogReceiver receiver = new LogReceiver();
        receiver.start();
    }
}
