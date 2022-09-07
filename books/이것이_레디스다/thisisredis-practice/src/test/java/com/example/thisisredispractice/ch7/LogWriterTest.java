package com.example.thisisredispractice.ch7;


import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class LogWriterTest {

    private static JedisHelper jedisHelper;

    private static LogWriter logger;

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        jedisHelper = JedisHelper.getInstance();
        logger = new LogWriter(jedisHelper);
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        jedisHelper.destroyPool();
    }

    @Test
    void testLogger() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            assertThat(logger.log(i + ", This is test log message")).isPositive();

            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }
}