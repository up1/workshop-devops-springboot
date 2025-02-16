package com.example.banking.repository;

import com.example.banking.model.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("ตรวจสอบการดึงข้อมูลของ account")
    public void case01() {
        Account account = new Account();
        account.setUsername("user1");
        account.setPassword("pass"); // Plain text password
        account.setBalance(BigDecimal.ZERO); // Initial balance set to 0
        account.setAuthorities(List.of(new SimpleGrantedAuthority("USER")));
        accountRepository.saveAndFlush(account);

        assertTrue(accountRepository.findByUsername("user1").isPresent());
        assertEquals("user1", accountRepository.findByUsername("user1").get().getUsername());
    }

}