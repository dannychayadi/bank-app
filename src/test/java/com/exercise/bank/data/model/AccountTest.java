package com.exercise.bank.data.model;

import com.exercise.bank.BankApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
public class AccountTest {

    @Autowired
    private Account account;

    @BeforeEach
    public void beforeEach() {
        account = new Account("0", "0245678912", LocalDateTime.of(2012, 02, 03, 13, 20));
    }

    @Test
    @DisplayName(value = "Assert Model Not Null")
    public void assertModelNotNull() {
        assertNotNull(account);
    }

    @Test
    @DisplayName(value = "Assert Setters")
    public void assertSetters() {
        account.setBalance("100");
        account.setAccountNumber("0231231426");

        assertEquals("100", account.getBalance());
        assertNotEquals("0", account.getBalance());

        assertEquals("0231231426", account.getAccountNumber());
        assertNotEquals("0245678912", account.getAccountNumber());
    }

    @BeforeEach
    public void afterEach() {

    }
}
