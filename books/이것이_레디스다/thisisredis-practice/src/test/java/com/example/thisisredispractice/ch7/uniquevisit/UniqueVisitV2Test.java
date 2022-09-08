package com.example.thisisredispractice.ch7.uniquevisit;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UniqueVisitV2Test {

    static JedisHelper helper;

    private UniqueVisitV2 uniqueVisit;

    private static final int TOTAL_USER = 10_000_000;

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
        this.uniqueVisit = new UniqueVisitV2(helper);

        this.uniqueVisit.visit(7, "20130510");
        this.uniqueVisit.visit(11, "20130510");
        this.uniqueVisit.visit(15, "20130510");
        this.uniqueVisit.visit(TOTAL_USER, "20130510");

        this.uniqueVisit.visit(3, "20130511");
        this.uniqueVisit.visit(7, "20130511");
        this.uniqueVisit.visit(9, "20130511");
        this.uniqueVisit.visit(11, "20130511");
        this.uniqueVisit.visit(15, "20130511");
        this.uniqueVisit.visit(TOTAL_USER, "20130511");

        this.uniqueVisit.visit(7, "20130512");
        this.uniqueVisit.visit(12, "20130512");
        this.uniqueVisit.visit(13, "20130512");
        this.uniqueVisit.visit(15, "20130512");
        this.uniqueVisit.visit(TOTAL_USER, "20130512");
    }

    @Test
    void uvSum() {
        String[] dateList1 = { "20130510", "20130511", "20130512" };
        assertThat(uniqueVisit.getUVSum(dateList1)).isEqualTo(3);

        String[] dateList2 = { "20130510", "20130511", "20130512", "20110512" };
        assertThat(uniqueVisit.getUVSum(dateList2)).isZero();

        String[] dateList3 = {
                "20130510", "20130511", "20130512", "20130511", "20130512", "20130511", "20130512", "20110512"
        };
        assertThat(uniqueVisit.getUVSum(dateList3)).isZero();
    }
}