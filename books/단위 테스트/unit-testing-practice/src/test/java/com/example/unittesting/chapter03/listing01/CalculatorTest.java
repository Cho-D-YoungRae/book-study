package com.example.unittesting.chapter03.listing01;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    @Test
    void sum_of_two_numbers() {
        // Arrange
        double first = 10;
        double second = 20;
        Calculator sut = new Calculator();

        // Act
        double result = sut.sum(first, second);

        // Assert
        assertThat(result).isEqualTo(30);
    }
}