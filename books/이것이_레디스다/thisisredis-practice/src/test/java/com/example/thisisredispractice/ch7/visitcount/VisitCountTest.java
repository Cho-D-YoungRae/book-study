package com.example.thisisredispractice.ch7.visitcount;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VisitCountTest {

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
        assertThat(visitCount).isNotNull();

        assertThat(visitCount.addVisit("52")).isPositive();
        assertThat(visitCount.addVisit("180")).isPositive();
        assertThat(visitCount.addVisit("554")).isPositive();
    }

    @Test
    void getVisitCount() {
        VisitCount visitCount = new VisitCount(helper);
        assertThat(visitCount).isNotNull();

        List<Long> result = visitCount.getVisitCountByDate("52", "180", "554");
        assertThat(result)
                .isNotNull()
                .hasSize(3);

        long sumCount = result.stream()
                .mapToLong(count -> count)
                .sum();
        long totalCount = visitCount.getVisitTotalCount();
        assertThat(sumCount).isEqualTo(totalCount);
    }
}