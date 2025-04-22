package com.example.unittesting.chapter02.listing02;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class CustomerTest {

    @Test
    void purchase_succeeds_when_enough_inventory() {
        // Arrange
        IStore storeMock = mock(IStore.class);
        given(storeMock.hasEnoughInventory(Product.SHAMPOO, 5)).willReturn(true);
        Customer customer = new Customer();

        // Act
        boolean result = customer.purchase(storeMock, Product.SHAMPOO, 5);

        // Assert
        assertThat(result).isTrue();
        then(storeMock).should().removeInventory(Product.SHAMPOO, 5);
    }

    @Test
    void purchase_fails_when_not_enough_inventory() {
        // Arrange
        IStore storeMock = mock(IStore.class);
        given(storeMock.hasEnoughInventory(Product.SHAMPOO, 5)).willReturn(false);
        Customer customer = new Customer();

        // Act
        boolean result = customer.purchase(storeMock, Product.SHAMPOO, 5);

        // Assert
        assertThat(result).isFalse();
        then(storeMock).should(never()).removeInventory(Product.SHAMPOO, 5);
    }
}