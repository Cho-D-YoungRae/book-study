package com.example.unittesting.chapter03;

import com.example.unittesting.chapter02.listing01.Customer;
import com.example.unittesting.chapter02.listing01.Product;
import com.example.unittesting.chapter02.listing01.Store;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest_4 {


    @Test
    void purchase_succeeds_when_enough_inventory() {
        // Arrange
        Store store = createStoreWithInventory(Product.SHAMPOO, 10);
        Customer sut = createCustomer();

        // Act
        boolean result = sut.purchase(store, Product.SHAMPOO, 5);

        // Assert
        assertThat(result).isTrue();
        assertThat(store.getInventory(Product.SHAMPOO)).isEqualTo(5);
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {
        // Arrange
        Store store = createStoreWithInventory(Product.SHAMPOO, 10);
        Customer sut = createCustomer();

        // Act
        boolean result = sut.purchase(store, Product.SHAMPOO, 15);

        // Assert
        assertThat(result).isFalse();
        assertThat(store.getInventory(Product.SHAMPOO)).isEqualTo(10);
    }
    
    private Store createStoreWithInventory(Product product, int quantity) {
        Store store = new Store();
        store.addInventory(product, quantity);
        return store;
    }

    private Customer createCustomer() {
        return new Customer();
    }
}
