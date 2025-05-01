package com.example.unittesting.chapter07.refactored_3;

public class CompanyFactory {

    public static Company create(Object[] data) {
        if (data == null || data.length < 2) {
            throw new IllegalArgumentException();
        }

        String domainName = (String) data[0];
        int numberOfEmployees = (int) data[1];

        return new Company(domainName, numberOfEmployees);
    }
}
