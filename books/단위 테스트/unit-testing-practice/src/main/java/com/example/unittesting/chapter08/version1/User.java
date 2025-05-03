package com.example.unittesting.chapter08.version1;


import java.util.List;

public record User(
        long id,
        String email,
        UserType type,
        boolean emailConfirmed,
        List<EmailChangedEvent> emailChangedEvents
) {

    public User(long id, String email, UserType type, boolean isEmailConfirmed) {
        this(id, email, type, isEmailConfirmed, List.of());
    }

    public boolean canChangeEmail() {
        return !emailConfirmed;
    }

    public UserEmailChangedResult changeEmail(String newEmail, Company company) {
        if (!canChangeEmail()) {
            throw new IllegalStateException();
        }

        if (email.equals(newEmail)) {
            return new UserEmailChangedResult(this, company);
        }

        UserType newType = company.isEmailCorporate(newEmail) ? UserType.EMPLOYEE : UserType.CUSTOMER;
        if (type != newType) {
            int delta = newType == UserType.EMPLOYEE ? 1 : -1;
            company = company.changeNumberOfEmployees(delta);
        }

        return new UserEmailChangedResult(
                new User(id, newEmail, newType, emailConfirmed, List.of(new EmailChangedEvent(id, newEmail))),
                company
        );
    }
}
