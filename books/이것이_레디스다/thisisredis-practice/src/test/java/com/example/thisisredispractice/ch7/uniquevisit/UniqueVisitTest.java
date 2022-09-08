package com.example.thisisredispractice.ch7.uniquevisit;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


class UniqueVisitTest {

    static JedisHelper helper;

    private UniqueVisit uniqueVisit;

    private static final int VISIT_COUNT = 1000;

    private static final int TOTAL_USER = 10_000_000;

    private static final String TEST_DATE = "19500101";

    static Random rand = new Random();

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        helper = JedisHelper.getInstance();
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        helper.destroyPool();
    }

    @BeforeEach
    public void setUp() throws Exception {
        this.uniqueVisit = new UniqueVisit(helper);
    }

    @Test
    void randomPV() {
        int pv = uniqueVisit.getPVCount(getToday());
        for (int i = 0; i < VISIT_COUNT; i++) {
            uniqueVisit.visit(rand.nextInt(TOTAL_USER));
        }
        assertThat(uniqueVisit.getPVCount(getToday())).isEqualTo(pv + VISIT_COUNT);
    }

    @Test
    void invalidPV() {
        assertThat(uniqueVisit.getPVCount(TEST_DATE)).isZero();
        assertThat(uniqueVisit.getUVCount(TEST_DATE)).isZero();
    }

    @Test
    void pv() {
        int result = uniqueVisit.getPVCount(getToday());
        uniqueVisit.visit(65487);
        assertThat(uniqueVisit.getPVCount(getToday())).isEqualTo(result + 1);
    }

    @Test
    void uv() {
        uniqueVisit.visit(65487);
        Long result = uniqueVisit.getUVCount(getToday());
        uniqueVisit.visit(65487);

        assertThat(uniqueVisit.getUVCount(getToday())).isEqualTo(result);
    }

    private String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }
}