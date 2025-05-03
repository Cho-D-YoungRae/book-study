package com.example.unittesting.chapter08.version1;

public record EmailChangedEvent(
        long userId,
        String newEmail
) {
}
