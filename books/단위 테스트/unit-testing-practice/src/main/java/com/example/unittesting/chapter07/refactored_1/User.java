package com.example.unittesting.chapter07.refactored_1;

public class User {

    private int userId;
    private String email;
    private UserType type;

    public User(int userId, String email, UserType type) {
        this.userId = userId;
        this.email = email;
        this.type = type;
    }

    public int changeEmail(String newEmail, String companyDomainName, int numberOfEmployees) {
        if (email.equals(newEmail)) {
            return numberOfEmployees;
        }

        String emailDomain = newEmail.split("@")[1];
        boolean isEmailCorporate = emailDomain.equals(companyDomainName);
        UserType newType = isEmailCorporate ? UserType.EMPLOYEE : UserType.CUSTOMER;
        if (type != newType) {
            int delta = newType == UserType.EMPLOYEE ? 1 : -1;
            numberOfEmployees += delta;
        }

        this.email = newEmail;
        this.type = newType;

        return numberOfEmployees;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public UserType getType() {
        return type;
    }
}
