package com.example.unittesting.chapter9.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(EventDispatcher.class);

    private final MessageBus messageBus;
    private final IDomainLogger domainLogger;

    public EventDispatcher(MessageBus messageBus, IDomainLogger domainLogger) {
        this.messageBus = messageBus;
        this.domainLogger = domainLogger;
    }

    public void dispatch(List<IDomainEvent> events) {
        for (IDomainEvent event : events) {
            doDispatch(event);
        }
    }

    private void doDispatch(IDomainEvent event) {
        switch (event) {
            case EmailChangedEvent(long userId, String newEmail) ->
                    messageBus.sendEmailChangedMessage(userId, newEmail);
            case UserTypeChangedEvent(long userId, UserType oldUserType, UserType newUserType) ->
                    domainLogger.userTypeChanged(
                            userId,
                            oldUserType,
                            newUserType
                    );
            default -> log.warn("Unknown event type: {}", event.getClass().getName());
        }
    }
}
