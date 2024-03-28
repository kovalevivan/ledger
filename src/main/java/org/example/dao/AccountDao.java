package org.example.dao;

import org.example.domain.Account;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDao {

    private static final Map<String, Account> balancesPerAccounts = new ConcurrentHashMap<>();


    public Optional<Account> get(UUID accountId) {
        return Optional.ofNullable(balancesPerAccounts.get(accountId.toString()));
    }

    public Account create(UUID accountId, Long balance) {
        final var account = new Account(accountId, balance);
        balancesPerAccounts.put(accountId.toString(), account);
        return account;
    }

}
