package com.example.unittesting.chapter9.v2;

public interface IDomainLogger {

    void userTypeChanged(long userId, UserType oldUserType, UserType newUserType);
}
