package com.exercise.bank.business.service;

import com.exercise.bank.BankApplication;
import com.exercise.bank.data.model.Account;
import com.exercise.bank.data.model.Member;
import com.exercise.bank.data.model.TransactionHistory;
import com.exercise.bank.data.repository.AccountRepository;
import com.exercise.bank.data.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class AccountServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private Account account;

    @Autowired
    private Member member;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    private static String recipientAccountNumber;

    @Value(value = "${sql.script.create.member}")
    public String sqlCreateMember;

    @Value(value = "${sql.script.create.roles}")
    public String sqlCreateRoles;

    @Value(value = "${sql.script.create.account}")
    public String sqlCreateAccount;

    @Value(value = "${sql.script.create.member2}")
    public String sqlCreateMember2;

    @Value(value = "${sql.script.create.roles2}")
    public String sqlCreateRoles2;

    @Value(value = "${sql.script.create.account2}")
    public String sqlCreateAccount2;

    @Value(value = "${sql.script.balance.account.add}")
    public String sqlAddBalanceAccount;

    @Value(value = "${sql.script.balance.account2.add}")
    public String sqlAddBalanceAccount2;

    @Value(value = "${sql.script.create.transaction_history}")
    public String sqlCreateTransactionHistory;

    @Value(value = "${sql.script.create.transaction_history2}")
    public String sqlCreateTransactionHistory2;

    @Value(value = "${sql.script.delete.member}")
    public String sqlDeleteMember;

    @Value(value = "${sql.script.delete.roles}")
    public String sqlDeleteRoles;

    @Value(value = "${sql.script.delete.account}")
    public String sqlDeleteAccount;

    @Value(value = "${sql.script.delete.transaction_history}")
    public String sqlDeleteTransactionHistory;

    @BeforeAll
    public static void setup() {
        recipientAccountNumber = "0139485435";
    }

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateMember);
        jdbc.execute(sqlCreateRoles);
        jdbc.execute(sqlCreateAccount);
        jdbc.execute(sqlCreateMember2);
        jdbc.execute(sqlCreateRoles2);
        jdbc.execute(sqlCreateAccount2);
        jdbc.execute(sqlAddBalanceAccount);
        jdbc.execute(sqlAddBalanceAccount2);
        jdbc.execute(sqlCreateTransactionHistory);
        jdbc.execute(sqlCreateTransactionHistory2);
    }

    @Test
    @DisplayName(value = "Transfer Balance")
    public void transferBalance() {
        member = memberService.findByUsername("userdemo");
        assertNotNull(member);

        Boolean result = accountService.transferBalance(member, "10.00", recipientAccountNumber);
        assertTrue(result);

        // get balance of recipient
        Account accountRecipient = accountRepository.findByAccountNumber(recipientAccountNumber);
        assertEquals("110.00", accountRecipient.getBalance());

        List<TransactionHistory> transactionHistories = transactionHistoryRepository.findAllByMemberId(member.getId());
        assertEquals(2, transactionHistories.size());
    }

    @Test
    @DisplayName(value = "Transfer Balance Exceed Amount Assert False")
    public void transferBalanceExceedAmountAssertFalse() {
        member = memberService.findByUsername("userdemo");
        assertNotNull(member);

        Boolean result = accountService.transferBalance(member, "110.00", recipientAccountNumber);
        assertFalse(result);
    }

    @Test
    @DisplayName(value = "Transfer Balance Invalid Amount Assert False")
    public void transferBalanceInvalidAmountAssertFalse() {
        member = memberService.findByUsername("userdemo");
        assertNotNull(member);

        assertFalse(accountService.transferBalance(member, "0.00", recipientAccountNumber));
        assertFalse(accountService.transferBalance(member, "-10.00", recipientAccountNumber));
    }

    @Test
    @DisplayName(value = "Transfer Balance Invalid Account Number Assert False")
    public void transferBalanceInvalidAccountNumberAssertFalse() {
        member = memberService.findByUsername("userdemo");
        assertNotNull(member);

        assertFalse(accountService.transferBalance(member, "10.00", "0001234500"));
    }

    @Test
    @DisplayName(value = "Transfer Balance Own Account Number Assert False")
    public void transferBalanceOwnAccountNumberAssertFalse() {
        member = memberService.findByUsername("userdemo");
        assertNotNull(member);

        System.out.println(member.getAccount().getAccountNumber());

        assertFalse(accountService.transferBalance(member, "10.00", "1234567891"));
    }

    @Test
    @DisplayName(value = "Transfer Balance Empty Value Assert False")
    public void transferBalanceEmptyValueAssertFalse() {
        member = memberService.findByUsername("userdemo");
        assertNotNull(member);

        assertFalse(accountService.transferBalance(member, "", recipientAccountNumber));
        assertFalse(accountService.transferBalance(member, "10.00", ""));
        assertFalse(accountService.transferBalance(member, "", ""));
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteAccount);
        jdbc.execute(sqlDeleteRoles);
        jdbc.execute(sqlDeleteTransactionHistory);
        jdbc.execute(sqlDeleteMember);
    }
}
