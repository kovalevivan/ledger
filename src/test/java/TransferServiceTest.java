import org.example.dao.AccountBalanceDao;
import org.example.dto.TransferRequestDto;
import org.example.exception.InvalidTransferException;
import org.example.service.TransferService;
import org.junit.jupiter.api.Test;

import static org.example.dao.AccountBalanceTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransferServiceTest {

    AccountBalanceDao accountBalanceDao = new AccountBalanceDao();
    TransferService transferService = new TransferService(accountBalanceDao);

    @Test
    public void givenNegativeAmount_whenTransfer_thenThrowException() {
        //given
        final var transferRequestDto = new TransferRequestDto(ACCOUNT_1, ACCOUNT_2, -1);

        //when
        assertThrows(InvalidTransferException.class,
                () -> transferService.transfer(transferRequestDto));
    }

    @Test
    public void givenNotExistedAccount_whenTransfer_thenThrowException() {
        //given
        final var transferRequestDto = new TransferRequestDto(ACCOUNT_1, NOT_EXISTED_ACCOUNT, 1);

        assertThrows(InvalidTransferException.class,
                () -> transferService.transfer(transferRequestDto));
    }

    @Test
    public void givenValidAccounts_whenTransfer_thenMoneySuccess() {
        //given
        final var transferRequestDto = new TransferRequestDto(ACCOUNT_1, ACCOUNT_2, 1);

        //when
        transferService.transfer(transferRequestDto);

        //then
        assertEquals(99, accountBalanceDao.getBalance(ACCOUNT_1).get());
        assertEquals(101, accountBalanceDao.getBalance(ACCOUNT_2).get());
    }
}
