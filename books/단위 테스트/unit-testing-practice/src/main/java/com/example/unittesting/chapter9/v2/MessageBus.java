package com.example.unittesting.chapter9.v2;

public class MessageBus {

    private final IBus bus;

    public MessageBus(IBus bus) {
        this.bus = bus;
    }

    public void sendEmailChangedMessage(long userId, String newEmail) {
        bus.send(String.format(
                "Type: USER EMAIL CHANGED; Id: %d; NewEmail: %s",
                userId, newEmail
        ));
    }
}
