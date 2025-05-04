package com.example.unittesting.chapter9.v2;


import java.util.ArrayList;
import java.util.List;

public record User(
        long id,
        String email,
        UserType type,
        boolean emailConfirmed,
        List<IDomainEvent> domainEvents
) {

    public User(long id, String email, UserType type, boolean isEmailConfirmed) {
        this(id, email, type, isEmailConfirmed, List.of());
    }

    public boolean canChangeEmail() {
        return !emailConfirmed;
    }

    public UserEmailChangeResult changeEmail(String newEmail, Company company) {
        if (!canChangeEmail()) {
            throw new IllegalStateException();
        }

        if (email.equals(newEmail)) {
            return new UserEmailChangeResult(this, company);
        }

        List<IDomainEvent> events = new ArrayList<>();

        UserType newType = company.isEmailCorporate(newEmail) ? UserType.EMPLOYEE : UserType.CUSTOMER;
        if (type != newType) {
            int delta = newType == UserType.EMPLOYEE ? 1 : -1;
            company = company.changeNumberOfEmployees(delta);
            events.add(new UserTypeChangedEvent(id, type, newType));
        }

        events.add(new EmailChangedEvent(id, newEmail));
        User user = new User(id, newEmail, newType, emailConfirmed, events.stream().toList());
        return new UserEmailChangeResult(user, company);
    }
}
