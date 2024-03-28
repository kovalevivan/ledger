package org.example.dao;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.dao.AccountBalanceTestData.*;

public class AccountBalanceDao {

    private static final Map<String, AtomicInteger> balancesPerAccounts = new ConcurrentHashMap<>(Map.of(
            ACCOUNT_1, new AtomicInteger(100),
            ACCOUNT_2, new AtomicInteger(100),
            ACCOUNT_3, new AtomicInteger(100)
    ));

    public boolean isExist(String accountId) {
        return balancesPerAccounts.containsKey(accountId);
    }

    public AtomicInteger getBalance(String accountId) {
        return balancesPerAccounts.get(accountId);
    }

    public void add(String accountId, Integer delta) {
        Optional.ofNullable(balancesPerAccounts.get(accountId))
                .ifPresent(balance -> balance.addAndGet(delta));
    }
}
