import org.example.dao.AccountDao;
import org.example.dao.TransferDao;
import org.example.dto.TransferRequestDto;
import org.example.exception.InvalidTransferException;
import org.example.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransferServiceTest implements AccountTestData {

    AccountDao accountBalanceDao = new AccountDao();
    TransferDao transferDao = new TransferDao();
    TransferService transferService = new TransferService(accountBalanceDao, transferDao);

    @BeforeEach
    public void init() {
        accountBalanceDao.create(ACCOUNT_1, 100L);
        accountBalanceDao.create(ACCOUNT_2, 100L);
    }

    @Test
    public void givenNegativeAmount_whenTransfer_thenThrowException() {
        //given
        final var transferRequestDto = new TransferRequestDto(ACCOUNT_1, ACCOUNT_2, -1L);

        //when
        assertThrows(InvalidTransferException.class,
                () -> transferService.transfer(transferRequestDto));
    }

    @Test
    public void givenNotExistedAccount_whenTransfer_thenThrowException() {
        //given
        final var transferRequestDto = new TransferRequestDto(ACCOUNT_1, NOT_EXISTED_ACCOUNT, 1L);

        assertThrows(InvalidTransferException.class,
                () -> transferService.transfer(transferRequestDto));
    }

    @Test
    public void givenValidAccounts_whenTransfer_thenMoneySuccess() {
        //given
        final var transferRequestDto = new TransferRequestDto(ACCOUNT_1, ACCOUNT_2, 1L);

        //when
        transferService.transfer(transferRequestDto);

        //then
        final var account1 = accountBalanceDao.get(ACCOUNT_1).orElseThrow();
        final var account2 = accountBalanceDao.get(ACCOUNT_2).orElseThrow();

        assertEquals(99L, account1.getBalance());
        assertEquals(101L, account2.getBalance());
    }
}
