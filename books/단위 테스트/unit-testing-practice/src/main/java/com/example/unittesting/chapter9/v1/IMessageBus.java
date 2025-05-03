package com.example.unittesting.chapter9.v1;

public interface IMessageBus {

    void sendEmailChangedMessage(long userId, String newEmail);
}
