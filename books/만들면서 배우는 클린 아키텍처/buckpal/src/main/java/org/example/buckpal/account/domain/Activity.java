package org.example.buckpal.account.domain;

import lombok.NonNull;

import java.time.LocalDateTime;

public record Activity(
        ActivityId id,
        @NonNull Account.AccountId ownerAccountId,
        @NonNull Account.AccountId sourceAccountId,
        @NonNull Account.AccountId targetAccountId,
        @NonNull LocalDateTime timestamp,
        @NonNull Money money
) {

    public Activity(
            @NonNull final Account.AccountId ownerAccountId,
            @NonNull final Account.AccountId sourceAccountId,
            @NonNull final Account.AccountId targetAccountId,
            @NonNull final LocalDateTime timestamp,
            @NonNull final Money money
    ) {
        this(null, ownerAccountId, sourceAccountId, targetAccountId, timestamp, money);
    }

    public record ActivityId(Long value) {
    }
}
