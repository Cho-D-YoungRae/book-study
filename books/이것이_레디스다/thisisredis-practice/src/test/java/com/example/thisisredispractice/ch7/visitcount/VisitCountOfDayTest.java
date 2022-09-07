package com.example.thisisredispractice.ch7.visitcount;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VisitCountOfDayTest {

    static JedisHelper helper;

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        helper = JedisHelper.getInstance();
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        helper.destroyPool();
    }

    @Test
    void addVisit() {
        VisitCount visitCount = new VisitCount(helper);
        assertThat(visitCount.addVisit("52")).isPositive();
        assertThat(visitCount.addVisit("180")).isPositive();
        assertThat(visitCount.addVisit("554")).isPositive();

        VisitCountOfDay visitCountOfDay = new VisitCountOfDay(helper);
        assertThat(visitCountOfDay.addVisit("52")).isPositive();
        assertThat(visitCountOfDay.addVisit("180")).isPositive();
        assertThat(visitCountOfDay.addVisit("554")).isPositive();
    }

    @Test
    void getVisitCount() {
        VisitCountOfDay visitCountOfDay = new VisitCountOfDay(helper);
        String[] dates = {"20220907", "20220908", "20220909", "20220910"};

        List<Long> result = visitCountOfDay.getVisitCount("52", dates);
        assertThat(result)
                .isNotNull()
                .hasSize(4);
    }
}