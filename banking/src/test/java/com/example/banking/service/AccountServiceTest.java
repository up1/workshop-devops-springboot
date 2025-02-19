package com.example.banking.service;

import com.example.banking.model.Account;
import com.example.banking.model.Transaction;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("ทำการโอนเงินสำเร็จ")
    void case01() {
        // Arrange
        Account fromAccount = new Account();
        fromAccount.setUsername("Somkiat");
        fromAccount.setBalance(BigDecimal.valueOf(1000));

        String targetAccountName = "pui";

        AccountService accountService = new AccountService(accountRepository, transactionRepository);

        Account toAccount = new Account();
        toAccount.setUsername(targetAccountName);
        toAccount.setBalance(BigDecimal.valueOf(2000));
        when(accountRepository.findByUsername(targetAccountName)).thenReturn(Optional.of(toAccount));

        // Act
        accountService.transferAmount(fromAccount, targetAccountName, BigDecimal.valueOf(1000));

        // Assert
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }
}