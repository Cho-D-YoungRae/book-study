package com.example.unittesting.chapter06.listing01;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerControllerTest {

    @Test
    void discount_of_two_products() {
        Product product1 = new Product("Hand wash");
        Product product2 = new Product("Shampoo");
        PriceEngine sut = new PriceEngine();

        double discount = sut.calculateDiscount(List.of(product1, product2));

        assertThat(discount).isEqualTo(0.02);
    }
}