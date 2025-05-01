package com.example.unittesting.chapter07.sampleproject;

public class User {

    private int userId;

    private String email;

    private UserType type;

    public void changeEmail(int userId, String newEmail) {
        Object[] data = Database.getUserById(userId);
        this.userId = userId;
        this.email = (String) data[0];
        this.type = (UserType) data[1];

        if (email.equals(newEmail)) {
            return;
        }

        // bool isEmailTaken = Database.GetUserByEmail(newEmail) != null;
        // if (isEmailTaken)
        //     return "Email is taken";

        Object[] companyData = Database.getCompany();
        String companyDomainName = (String) companyData[0];
        int numberOfEmployees = (int) companyData[1];

        String emailDomain = newEmail.split("@")[1];
        boolean isEmailCorporate = emailDomain.equals(companyDomainName);
        UserType newType = isEmailCorporate ? UserType.EMPLOYEE : UserType.CUSTOMER;

        if (type != newType) {
            int delta = newType == UserType.EMPLOYEE ? 1 : -1;
            int newNumber = numberOfEmployees + delta;
            Database.saveCompany(newNumber);
        }

        this.email = newEmail;
        this.type = newType;

        Database.saveUser(this);
        MessageBus.sendEmailChangedMessage(userId, newEmail);
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
