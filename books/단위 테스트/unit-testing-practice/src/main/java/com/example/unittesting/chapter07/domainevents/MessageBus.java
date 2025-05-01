package com.example.unittesting.chapter07.domainevents;

public class MessageBus {

    private IBus bus;

    public void sendEmailChangedMessage(int userId, String newEmail) {
        bus.send(String.format(
                "Subject: User; Type: EMAIL CHANGED; Id: %d; NewEmail: %s",
                userId, newEmail
        ));
    }
}
