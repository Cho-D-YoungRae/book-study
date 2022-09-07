package com.example.thisisredispractice.ch7.visitcount;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;

import static org.assertj.core.api.Assertions.assertThat;

class VisitCountOfDayV2Test {

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

        VisitCountOfDayV2 visitCountOfDay = new VisitCountOfDayV2(helper);
        assertThat(visitCountOfDay.addVisit("52")).isPositive();
        assertThat(visitCountOfDay.addVisit("180")).isPositive();
        assertThat(visitCountOfDay.addVisit("554")).isPositive();
    }

    @Test
    void getVisitCount() {
        VisitCountOfDayV2 visitCountOfDay = new VisitCountOfDayV2(helper);
        SortedMap<String, Long> visitCount = visitCountOfDay.getVisitCountByDaily("554");
        assertThat(visitCount)
                .isNotNull()
                .size().isPositive();
        assertThat(visitCount.firstKey()).isNotNull();
        assertThat(visitCount.lastKey()).isNotNull();
        System.out.println("visitCount = " + visitCount);

        SortedMap<String, Long> totalVisit = visitCountOfDay.getVisitCountByDailyTotal();
        assertThat(totalVisit)
                .isNotNull()
                .size().isPositive();
        assertThat(totalVisit.firstKey()).isNotNull();
        assertThat(totalVisit.lastKey()).isNotNull();
        System.out.println("totalVisit = " + totalVisit);
    }
}