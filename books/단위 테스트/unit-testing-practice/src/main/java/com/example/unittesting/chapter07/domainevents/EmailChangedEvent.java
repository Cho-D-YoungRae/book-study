package com.example.unittesting.chapter07.domainevents;

public record EmailChangedEvent(
        int userId,
        String newEmail
) {
}
