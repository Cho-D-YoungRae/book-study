package com.example.unittesting.chapter07.refactored_3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void changing_email_without_changing_user_type() {
        Company company = new Company("mycorp.com", 1);
        User sut = new User(1, "user@mycorp.com", UserType.EMPLOYEE);

        sut.changeEmail("new@mycorp.com", company);

        assertThat(company.getNumberOfEmployees()).isEqualTo(1);
        assertThat(sut.getEmail()).isEqualTo("new@mycorp.com");
        assertThat(sut.getType()).isEqualTo(UserType.EMPLOYEE);
    }

    @Test
    void changing_email_from_corporate_to_non_corporate() {
        Company company = new Company("mycorp.com", 1);
        User sut = new User(1, "user@mycorp.com", UserType.EMPLOYEE);

        sut.changeEmail("new@gmail.com", company);

        assertThat(company.getNumberOfEmployees()).isEqualTo(0);
        assertThat(sut.getEmail()).isEqualTo("new@gmail.com");
        assertThat(sut.getType()).isEqualTo(UserType.CUSTOMER);
    }

    @Test
    void changing_email_from_non_corporate_to_corporate() {
        Company company = new Company("mycorp.com", 1);
        User sut = new User(1, "user@gmail.com", UserType.CUSTOMER);

        sut.changeEmail("new@mycorp.com", company);

        assertThat(company.getNumberOfEmployees()).isEqualTo(2);
        assertThat(sut.getEmail()).isEqualTo("new@mycorp.com");
        assertThat(sut.getType()).isEqualTo(UserType.EMPLOYEE);
    }

    @Test
    void changing_mail_to_the_same_one() {
        Company company = new Company("mycorp.com", 1);
        User sut = new User(1, "user@gmail.com", UserType.CUSTOMER);

        sut.changeEmail("user@gmail.com", company);

        assertThat(company.getNumberOfEmployees()).isEqualTo(1);
        assertThat(sut.getEmail()).isEqualTo("user@gmail.com");
        assertThat(sut.getType()).isEqualTo(UserType.CUSTOMER);
    }
}