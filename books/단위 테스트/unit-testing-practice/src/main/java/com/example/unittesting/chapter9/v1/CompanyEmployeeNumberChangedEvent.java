package com.example.unittesting.chapter9.v1;

public record CompanyEmployeeNumberChangedEvent(
        String domainName,
        int delta
) implements IDomainEvent {
}
