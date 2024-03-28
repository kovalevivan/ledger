package org.example.service;

import jakarta.inject.Inject;
import org.example.dao.AccountDao;
import org.example.dao.TransferDao;
import org.example.domain.Account;
import org.example.domain.Transfer;
import org.example.dto.TransferRequestDto;
import org.example.exception.InsufficientFundException;
import org.example.exception.InvalidTransferException;

import java.util.Objects;

public class TransferService {

    private AccountDao accountBalanceDao;
    private TransferDao transferDao;

    @Inject
    public TransferService(AccountDao accountBalanceDao, TransferDao transferDao) {
        this.accountBalanceDao = accountBalanceDao;
        this.transferDao = transferDao;
    }

    public Transfer transfer(TransferRequestDto transferRequestDto) {
        validateRequest(transferRequestDto);

        final var accountFrom = accountBalanceDao.get(transferRequestDto.getAccountFrom())
                .orElseThrow(() -> new InvalidTransferException("Account " + transferRequestDto.getAccountFrom() + " doesn't exist"));
        final var accountTo = accountBalanceDao.get(transferRequestDto.getAccountTo())
                .orElseThrow(() -> new InvalidTransferException("Account " + transferRequestDto.getAccountTo() + " doesn't exist"));

        synchronized (accountFrom) {
            validateSufficientFunds(transferRequestDto, accountFrom);
            final var transfer = transferDao.create(accountFrom.getId(), accountTo.getId(), transferRequestDto.getAmount());

            accountFrom.add(-1 * transferRequestDto.getAmount());
            accountTo.add(transferRequestDto.getAmount());

            transfer.complete();
            return transfer;
        }
    }

    private void validateRequest(TransferRequestDto transferRequestDto) {
        if (Objects.equals(transferRequestDto.getAccountFrom(), transferRequestDto.getAccountTo())) {
            throw new InvalidTransferException("accountFrom and accountTo can't be same");
        }

        if (transferRequestDto.getAmount() <= 0) {
            throw new InvalidTransferException("amount must be greater than 0");
        }
    }

    private static void validateSufficientFunds(TransferRequestDto transferRequestDto, Account accountFrom) {
        if (accountFrom.getBalance() < transferRequestDto.getAmount()) {
            throw new InsufficientFundException(transferRequestDto.getAccountFrom());
        }
    }
}
