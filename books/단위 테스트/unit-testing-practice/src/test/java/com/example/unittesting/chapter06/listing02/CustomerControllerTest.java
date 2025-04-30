package com.example.unittesting.chapter06.listing02;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerControllerTest {

    @Test
    void adding_a_product_to_an_order() {
        Product product = new Product("Hand wash");
        Order sut = new Order();

        sut.addProduct(product);

        Assertions.assertThat(sut.getProducts())
                .hasSize(1)
                .containsExactly(product);
    }

}