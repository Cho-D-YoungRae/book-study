package com.example.unittesting.chapter9.v2;

import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class BusSpy implements IBus {

    private final List<String> sentMessages = new ArrayList<>();

    @Override
    public void send(String message) {
        sentMessages.add(message);
    }

    public BusSpy shouldSendNumberOfMessages(int number) {
        assertThat(sentMessages).hasSize(number);
        return this;
    }

    public BusSpy withEmailChangedMessage(long userId, String newEmail) {
        String message = String.format(
                "Type: USER EMAIL CHANGED; Id: %d; NewEmail: %s",
                userId, newEmail
        );
        assertThat(sentMessages).contains(message);
        return this;
    }
}
