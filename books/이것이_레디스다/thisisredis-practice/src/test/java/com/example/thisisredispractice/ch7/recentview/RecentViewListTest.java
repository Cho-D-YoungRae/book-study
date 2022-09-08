package com.example.thisisredispractice.ch7.recentview;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecentViewListTest {

    static JedisHelper helper;

    private static final String TEST_USER = "123";

    private RecentViewList viewList;

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        helper = JedisHelper.getInstance();
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        helper.destroyPool();
    }

    @BeforeEach
    public void setUp() throws Exception {
        viewList = new RecentViewList(helper, TEST_USER);
    }

    @Test
    void add () {
        for (int i = 1; i < 50; i++) {
            viewList.add(String.valueOf(i));
        }
    }

    @Test
    void checkMaxSize() {
        int checkSize = 4;
        assertThat(viewList.getRecentViewList(checkSize)).hasSize(4);
    }

    @Test
    void checkProductNo() {
        viewList.add("50");
        assertThat(viewList.getRecentViewList()).hasSize(RecentViewList.LIST_MAX_SIZE);

        List<String> itemList = viewList.getRecentViewList(5);
        assertThat(itemList.get(0)).isEqualTo("50");
        for (String item : itemList) {
            System.out.println("item = " + item);
        }
    }
}