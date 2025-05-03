package com.example.unittesting.chapter08.version1;

public interface IMessageBus {

    void sendEmailChangedMessage(long userId, String newEmail);
}
