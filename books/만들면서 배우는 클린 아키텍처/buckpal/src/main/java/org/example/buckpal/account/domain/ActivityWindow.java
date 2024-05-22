package org.example.buckpal.account.domain;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityWindow {

    private final List<Activity> activities;

    public ActivityWindow(@NonNull final List<Activity> activities) {
        this.activities = new ArrayList<>(activities);
    }

    public ActivityWindow(@NonNull final Activity ...activities) {
        this(Arrays.asList(activities));
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    public void addActivity(@NonNull final Activity activity) {
        activities.add(activity);
    }

    public LocalDateTime getStartTimestamp() {
        return activities.stream()
                .min(Comparator.comparing(Activity::timestamp))
                .orElseThrow(IllegalStateException::new)
                .timestamp();
    }

    public LocalDateTime getEndTimestamp() {
        return activities.stream()
                .max(Comparator.comparing(Activity::timestamp))
                .orElseThrow(IllegalStateException::new)
                .timestamp();
    }

    public Money calculateBalance(Account.AccountId accountId) {
        final Money depositBalance = activities.stream()
                .filter(a -> a.targetAccountId().equals(accountId))
                .map(Activity::money)
                .reduce(Money.ZERO, Money::add);
        final Money withdrawalBalance = activities.stream()
                .filter(a -> a.sourceAccountId().equals(accountId))
                .map(Activity::money)
                .reduce(Money.ZERO, Money::add);
        return Money.add(depositBalance, withdrawalBalance);
    }

}
