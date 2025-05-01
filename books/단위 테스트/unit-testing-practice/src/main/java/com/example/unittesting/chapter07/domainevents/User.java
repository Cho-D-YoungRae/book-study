package com.example.unittesting.chapter07.domainevents;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userId;
    private String email;
    private UserType type;
    private boolean isEmailConfirmed;
    private final List<EmailChangedEvent> emailChangedEvents = new ArrayList<>();

    public User(int userId, String email, UserType type, boolean isEmailConfirmed) {
        this.userId = userId;
        this.email = email;
        this.type = type;
        this.isEmailConfirmed = isEmailConfirmed;
    }

    public boolean canChangeEmail() {
        return !isEmailConfirmed;
    }

    public void changeEmail(String newEmail, Company company) {
        if (!canChangeEmail()) {
            throw new IllegalStateException();
        }

        if (email.equals(newEmail)) {
            return;
        }

        UserType newType = company.isEmailCorporate(newEmail) ? UserType.EMPLOYEE : UserType.CUSTOMER;
        if (type != newType) {
            int delta = newType == UserType.EMPLOYEE ? 1 : -1;
            company.changeNumberOfEmployees(delta);
        }

        this.email = newEmail;
        this.type = newType;
        this.emailChangedEvents.add(new EmailChangedEvent(userId, newEmail));
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

    public boolean isEmailConfirmed() {
        return isEmailConfirmed;
    }

    public List<EmailChangedEvent> getEmailChangedEvents() {
        return emailChangedEvents.stream().toList();
    }
}
