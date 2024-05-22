package org.example.buckpal.account.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.buckpal.account.application.port.in.SendMoneyCommand;
import org.example.buckpal.account.application.port.in.SendMoneyUseCase;
import org.example.buckpal.account.domain.Account;
import org.example.buckpal.account.domain.Money;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    void sendMoney(
            @PathVariable final Long sourceAccountId,
            @PathVariable final Long targetAccountId,
            @PathVariable final Long amount
    ) {
        final SendMoneyCommand sendMoneyCommand = new SendMoneyCommand(
                new Account.AccountId(sourceAccountId),
                new Account.AccountId(targetAccountId),
                Money.of(amount)
        );
    }
}
