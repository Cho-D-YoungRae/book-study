package org.example.buckpal.account.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;
import org.example.buckpal.account.domain.Account;
import org.example.buckpal.account.domain.Money;
import org.example.buckpal.common.SelfValidating;

public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final Account.AccountId sourceAccountId;

    @NotNull
    private final Account.AccountId targetAccountId;

    @NotNull
    private final Money money;

    public SendMoneyCommand(
            final Account.AccountId sourceAccountId,
            final Account.AccountId targetAccountId,
            final Money money
    ) {
        this.money = money;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        validateSelf();
    }
}
