package com.example.unittesting.chapter9.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(EventDispatcher.class);

    private final IMessageBus messageBus;
    private final IDomainLogger domainLogger;
    private final Database database;

    public EventDispatcher(IMessageBus messageBus, IDomainLogger domainLogger, Database database) {
        this.messageBus = messageBus;
        this.domainLogger = domainLogger;
        this.database = database;
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
            case CompanyEmployeeNumberChangedEvent(String domainName, int delta) -> {
                Company company = database.getCompanyByDomainName(domainName);
                database.saveCompany(company.changeNumberOfEmployees(delta));
            }
            default -> log.warn("Unknown event type: {}", event.getClass().getName());
        }
    }
}
