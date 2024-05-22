package org.example.buckpal.account.application.port.out;

import org.example.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);
}
