package com.example.thisisredispractice.ch7.cart;

import com.example.thisisredispractice.JedisHelper;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CartTest {

    private static JedisHelper helper;

    private static final String TEST_USER = "12521";

    private Cart cart;

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        helper = JedisHelper.getInstance();
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        helper.destroyPool();
    }

    @BeforeEach
    void setUp() throws Exception {
        cart = new Cart(helper, TEST_USER);
    }

    @Test
    void addProduct() {
        assertThat(cart.addProduct("151", "원두커피", 1)).isEqualTo("OK");
        assertThat(cart.addProduct("156", "캔커피", 5)).isEqualTo("OK");
    }

    @Test
    void getProductList() {
        JSONArray products = cart.getProductList();

        assertThat(products)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    void flushCart() {
        assertThat(cart.flushCart()).isNotNegative();
        assertThat(cart.flushCartDeprecated()).isNotNegative();
    }
}