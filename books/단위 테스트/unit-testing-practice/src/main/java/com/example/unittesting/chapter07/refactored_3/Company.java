package com.example.unittesting.chapter07.refactored_3;

public class Company {

    private String domainName;
    private int numberOfEmployees;

    public Company(String domainName, int numberOfEmployees) {
        this.domainName = domainName;
        this.numberOfEmployees = numberOfEmployees;
    }

    public void changeNumberOfEmployees(int delta) {
        if (numberOfEmployees + delta < 0) {
            throw new IllegalArgumentException();
        }

        numberOfEmployees += delta;
    }

    public boolean isEmailCorporate(String email) {
        String emailDomain = email.split("@")[1];
        return emailDomain.equals(domainName);
    }

    public String getDomainName() {
        return domainName;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }
}
