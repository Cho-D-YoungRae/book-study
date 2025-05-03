package com.example.unittesting.chapter9.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomainLogger implements IDomainLogger {

    private static final Logger log = LoggerFactory.getLogger(DomainLogger.class);

    @Override
    public void userTypeChanged(long userId, UserType oldUserType, UserType newUserType) {
        log.info("User[{}] changed type from {} to {}", userId, oldUserType, newUserType);
    }
}
