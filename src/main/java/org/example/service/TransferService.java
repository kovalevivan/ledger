package org.example.service;

import jakarta.inject.Inject;
import org.example.dao.AccountBalanceDao;
import org.example.dto.TransferRequestDto;
import org.example.exception.InsufficientFundException;
import org.example.exception.InvalidTransferException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class TransferService {

    private static final Map<String, Lock> accountLocks = new ConcurrentHashMap<>();

    private AccountBalanceDao accountBalanceDao;

    @Inject
    public TransferService(AccountBalanceDao accountBalanceDao) {
        this.accountBalanceDao = accountBalanceDao;
    }

    public void transfer(TransferRequestDto transferRequestDto) {
        validateRequest(transferRequestDto);

        final var accountBalance = accountBalanceDao.getBalance(transferRequestDto.getAccountFrom());

        synchronized (accountBalance) {
            if (accountBalance.get() < transferRequestDto.getAmount()) {
                throw new InsufficientFundException(transferRequestDto.getAccountFrom());
            }

            accountBalanceDao.add(transferRequestDto.getAccountFrom(), -1 * transferRequestDto.getAmount());
            accountBalanceDao.add(transferRequestDto.getAccountTo(), transferRequestDto.getAmount());
        }
    }

    private void validateRequest(TransferRequestDto transferRequestDto) {
        if(Objects.equals(transferRequestDto.getAccountFrom(), transferRequestDto.getAccountTo())) {
            throw new InvalidTransferException("accountFrom and accountTo can't be same");
        }

        if(!accountBalanceDao.isExist(transferRequestDto.getAccountFrom())) {
            throw new InvalidTransferException("Account " + transferRequestDto.getAccountFrom() + " doesn't exist");
        } else if(!accountBalanceDao.isExist(transferRequestDto.getAccountTo())) {
            throw new InvalidTransferException("Account " + transferRequestDto.getAccountTo() + " doesn't exist");
        }

        if (transferRequestDto.getAmount() <= 0) {
            throw new InvalidTransferException("amount must be greater than 0");
        }
    }
}
