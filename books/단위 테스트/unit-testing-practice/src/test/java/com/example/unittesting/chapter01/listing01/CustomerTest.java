package com.example.unittesting.chapter01.listing01;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void purchase_succeeds_when_enough_inventory() {
        // Arrange
        Store store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        Customer customer = new Customer();

        // Act
        boolean result = customer.purchase(store, Product.SHAMPOO, 5);

        // Assert
        assertThat(result).isTrue();
        assertThat(store.getInventory(Product.SHAMPOO)).isEqualTo(5);
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {
        // Arrange
        Store store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        Customer customer = new Customer();

        // Act
        boolean result = customer.purchase(store, Product.SHAMPOO, 15);

        // Assert
        assertThat(result).isFalse();
        assertThat(store.getInventory(Product.SHAMPOO)).isEqualTo(10);
    }
}