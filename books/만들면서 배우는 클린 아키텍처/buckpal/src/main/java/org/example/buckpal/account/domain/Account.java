package org.example.buckpal.account.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Account {

    private AccountId id;
    private Money baselineBalance;
    private ActivityWindow activityWindow;

    public static Account withoutId(
            final Money baselineBalance,
            final ActivityWindow activityWindow
    ) {
        return new Account(null, baselineBalance, activityWindow);
    }

    public static Account withId(
            final AccountId accountId,
            final Money baselineBalance,
            final ActivityWindow activityWindow
    ) {
        return new Account(accountId, baselineBalance, activityWindow);
    }

    public Optional<AccountId> getId() {
        return Optional.ofNullable(id);
    }

    public Money calculateBalance() {
        return Money.add(
                baselineBalance,
                activityWindow.calculateBalance(id)
        );
    }

    public boolean withdraw(final Money money, final AccountId targetAccountId) {
        if (!mayWithdraw(money)) {
            return false;
        }
        final Activity withdrawal = new Activity(
                id,
                id,
                targetAccountId,
                LocalDateTime.now(),
                money
        );
        activityWindow.addActivity(withdrawal);
        return true;
    }

    private boolean mayWithdraw(final Money money) {
        return Money.add(
                calculateBalance(),
                money.negate()
        ).isPositiveOrZero();
    }

    public boolean deposit(final Money money, final AccountId sourceAccountId) {
        final Activity deposit = new Activity(
                id,
                sourceAccountId,
                id,
                LocalDateTime.now(),
                money
        );
        activityWindow.addActivity(deposit);
        return true;
    }

    public record AccountId(Long value) {}
}
