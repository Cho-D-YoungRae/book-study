package com.example.unittesting.chapter07.sampleproject;

public class MessageBus {

    private static IBus bus;

    public static void sendEmailChangedMessage(int userId, String newEmail) {
        bus.send(String.format(
                "Subject: User; Type: EMAIL CHANGED; Id: %d; NewEmail: %s",
                userId, newEmail
        ));
    }
}
