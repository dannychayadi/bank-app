package com.exercise.bank.data.repository;

import com.exercise.bank.BankApplication;
import com.exercise.bank.data.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class AccountRepositoryTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private AccountRepository accountRepository;

    @Value(value = "${sql.script.create.member}")
    public String sqlCreateMember;

    @Value(value = "${sql.script.create.account}")
    public String sqlCreateAccount;

    @Value(value = "${sql.script.create.member2}")
    public String sqlCreateMember2;

    @Value(value = "${sql.script.create.account2}")
    public String sqlCreateAccount2;

    @Value(value = "${sql.script.balance.account.add}")
    public String sqlAddBalanceAccount;

    @Value(value = "${sql.script.balance.account2.add}")
    public String sqlAddBalanceAccount2;

    @Value(value = "${sql.script.delete.member}")
    public String sqlDeleteMember;

    @Value(value = "${sql.script.delete.account}")
    public String sqlDeleteAccount;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateMember);
        jdbc.execute(sqlCreateAccount);
        jdbc.execute(sqlCreateMember2);
        jdbc.execute(sqlCreateAccount2);
        jdbc.execute(sqlAddBalanceAccount);
        jdbc.execute(sqlAddBalanceAccount2);
    }

    @Test
    @DisplayName(value = "Find By Account Number")
    public void findByAccountNumber() {
        Account account = accountRepository.findByAccountNumber("0139485435");
        assertNotNull(account);
    }

    @Test
    @DisplayName(value = "Find By Account Number Return Null")
    public void findByAccountNumberReturnNull() {
        Account account = accountRepository.findByAccountNumber("00012200121");
        assertNull(account);

        Account account2 = accountRepository.findByAccountNumber("000122");
        assertNull(account2);

        Account account3 = accountRepository.findByAccountNumber("");
        assertNull(account3);
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteAccount);
        jdbc.execute(sqlDeleteMember);
    }
}
