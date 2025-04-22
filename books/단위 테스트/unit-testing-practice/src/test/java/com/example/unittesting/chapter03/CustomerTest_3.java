package com.example.unittesting.chapter03;

import com.example.unittesting.chapter02.listing01.Customer;
import com.example.unittesting.chapter02.listing01.Product;
import com.example.unittesting.chapter02.listing01.Store;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest_3 {

    private Store store;
    private Customer sut;


    @Test
    void purchase_succeeds_when_enough_inventory() {
        // Arrange
        store = new Store();
        store.addInventory(Product.SHAMPOO, 10);
        sut = new Customer();

        // Act
        boolean result = sut.purchase(store, Product.SHAMPOO, 5);

        // Assert
        assertThat(result).isTrue();
        assertThat(store.getInventory(Product.SHAMPOO)).isEqualTo(5);
    }
}
