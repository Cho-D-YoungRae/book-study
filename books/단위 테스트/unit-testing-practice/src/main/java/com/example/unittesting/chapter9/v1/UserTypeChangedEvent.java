package com.example.unittesting.chapter9.v1;

public record UserTypeChangedEvent(
        long userId,
        UserType oldUserType,
        UserType newUserType
) implements IDomainEvent {
}
