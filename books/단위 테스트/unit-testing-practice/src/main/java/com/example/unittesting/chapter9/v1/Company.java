package com.example.unittesting.chapter9.v1;

public record Company(
        String domainName,
        int numberOfEmployees
) {

    public Company changeNumberOfEmployees(int delta) {
        if (numberOfEmployees + delta < 0) {
            throw new IllegalArgumentException();
        }

        return new Company(domainName, numberOfEmployees + delta);
    }

    public boolean isEmailCorporate(String email) {
        String emailDomain = email.split("@")[1];
        return emailDomain.equals(domainName);
    }
}
