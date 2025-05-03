package com.example.unittesting.chapter9.v1;

public record EmailChangedEvent(
        long userId,
        String newEmail
) implements IDomainEvent {
}
