package org.example.dao;

import org.example.domain.Account;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDao {

    private static final Map<String, Account> balancesPerAccounts = new ConcurrentHashMap<>();

    // for testing
    static {
        final var TEST_UUID_1 = UUID.fromString("dd5e091d-fc50-4d39-a958-8892873a6829");
        final var TEST_UUID_2 = UUID.fromString("902cc3fe-bcd2-4072-a069-d3688235f8bc");

        balancesPerAccounts.put(TEST_UUID_1.toString(), new Account(TEST_UUID_1, 100L));
        balancesPerAccounts.put(TEST_UUID_2.toString(), new Account(TEST_UUID_2, 100L));
    }


    public Optional<Account> get(UUID accountId) {
        return Optional.ofNullable(balancesPerAccounts.get(accountId.toString()));
    }

    public Account create(UUID accountId, Long balance) {
        final var account = new Account(accountId, balance);
        balancesPerAccounts.put(accountId.toString(), account);
        return account;
    }

}
