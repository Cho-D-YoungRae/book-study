package org.example.buckpal.account.application.service;

import lombok.RequiredArgsConstructor;
import org.example.buckpal.account.application.port.in.GetAccountBalanceQuery;
import org.example.buckpal.account.application.port.out.LoadAccountPort;
import org.example.buckpal.account.domain.Account;
import org.example.buckpal.account.domain.Money;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(final Account.AccountId accountId) {
        final Account account = loadAccountPort.loadAccount(accountId, LocalDateTime.now());
        return account.calculateBalance();
    }
}
