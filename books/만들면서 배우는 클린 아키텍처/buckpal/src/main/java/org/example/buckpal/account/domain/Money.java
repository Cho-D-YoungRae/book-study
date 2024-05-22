package org.example.buckpal.account.domain;

import lombok.NonNull;

import java.math.BigInteger;

public record Money(
        @NonNull BigInteger amount
) {

    public static Money ZERO = new Money(BigInteger.ZERO);

    public boolean isPositiveOrZero() {
        return this.amount().compareTo(BigInteger.ZERO) >= 0;
    }

    public boolean isNegative() {
        return this.amount().compareTo(BigInteger.ZERO) < 0;
    }

    public boolean isPositive() {
        return this.amount().compareTo(BigInteger.ZERO) > 0;
    }

    public boolean isGreaterThanOrEqualTo(final Money money) {
        return this.amount().compareTo(money.amount()) >= 0;
    }

    public boolean isGreaterThan(final Money money) {
        return this.amount().compareTo(money.amount()) > 0;
    }

    public static Money of(final long value) {
        return new Money(BigInteger.valueOf(value));
    }

    public static Money add(final Money a, final Money b) {
        return new Money(a.amount().add(b.amount()));
    }

    public static Money subtract(final Money a, final Money b) {
        return new Money(a.amount().subtract(b.amount()));
    }

    public Money minus(final Money money) {
        return new Money(this.amount().subtract(money.amount()));
    }

    public Money plus(final Money money) {
        return new Money(this.amount().add(money.amount()));
    }

    public Money negate() {
        return new Money(this.amount().negate());
    }
}
