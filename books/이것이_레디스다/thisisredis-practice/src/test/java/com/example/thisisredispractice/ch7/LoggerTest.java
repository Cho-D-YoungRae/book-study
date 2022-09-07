package com.example.thisisredispractice.ch7;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerTest {

    private static JedisHelper jedisHelper;

    private static final int WAITING_TERM = 5000;

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        jedisHelper = JedisHelper.getInstance();
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        jedisHelper.destroyPool();
    }

    @Test
    void testWrite() {
        Random random = new Random();
        LogWriterV2 logWriter = new LogWriterV2(jedisHelper);
        for (int i = 0; i < 100; i++) {
            assertThat(logWriter.log(i + ", This is test log message")).isPositive();
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    @Test
    void testReceiver() {
        LogReceiverV2 logReceiver = new LogReceiverV2();
        for (int i = 0; i < 5; i++) {
            logReceiver.start();
            try {
                Thread.sleep(WAITING_TERM);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }
}
