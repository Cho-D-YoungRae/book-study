package com.example.unittesting.chapter07.refactored_3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyTest {

    @ParameterizedTest
    @MethodSource("arguments")
    void differentiates_a_corporate_email_from_non_corporate_email(
            String domain, String email, boolean expectedResult
    ) {
        Company sut = new Company(domain, 0);

        boolean result = sut.isEmailCorporate(email);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments("mycorp.com", "email@mycorp.com", true),
                Arguments.arguments("mycorp.com", "email@gmail.com", false)
        );
    }
}