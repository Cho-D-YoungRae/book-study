package com.example.unittesting.chapter08.version1;

public class MessageBus implements IMessageBus {

    private final IBus bus;

    public MessageBus(IBus bus) {
        this.bus = bus;
    }

    @Override
    public void sendEmailChangedMessage(long userId, String newEmail) {
        bus.send(String.format(
                "Subject: User; Type: EMAIL CHANGED; Id: %d; NewEmail: %s",
                userId, newEmail
        ));
    }
}
