package com.exercise.bank.data.repository;

import com.exercise.bank.BankApplication;
import com.exercise.bank.data.model.Member;
import com.exercise.bank.data.model.TransactionHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class TransactionHistoryRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private JdbcTemplate jdbc;

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

    @Value(value = "${sql.script.create.transaction_history}")
    public String sqlCreateTransactionHistory;

    @Value(value = "${sql.script.create.transaction_history2}")
    public String sqlCreateTransactionHistory2;

    @Value(value = "${sql.script.delete.member}")
    public String sqlDeleteMember;

    @Value(value = "${sql.script.delete.account}")
    public String sqlDeleteAccount;

    @Value(value = "${sql.script.delete.transaction_history}")
    public String sqlDeleteTransactionHistory;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateMember);
        jdbc.execute(sqlCreateAccount);
        jdbc.execute(sqlCreateMember2);
        jdbc.execute(sqlCreateAccount2);
        jdbc.execute(sqlAddBalanceAccount);
        jdbc.execute(sqlAddBalanceAccount2);
        jdbc.execute(sqlCreateTransactionHistory);
        jdbc.execute(sqlCreateTransactionHistory2);
    }

    @Test
    @DisplayName(value = "Create Transaction History")
    public void createTransactionHistory() {
        // get member by username
        Member member = memberRepository.findByUsername("userdemo");
        assertNotNull(member);

        // create transaction history object model
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount("10.00");
        transactionHistory.setRecipientAccountNumber("0139485435");
        transactionHistory.setDescription("Transfer");
        transactionHistory.setCreatedDate(LocalDateTime.now());
        transactionHistory.setMember(member);

        // save to database
        transactionHistoryRepository.save(transactionHistory);

        // get transaction history from database
        List<TransactionHistory> transactionHistories = new ArrayList<>();
        Iterable<TransactionHistory> iterableTransactionHistory = transactionHistoryRepository.findAll();

        for (TransactionHistory eachTransactionHistory : iterableTransactionHistory) {
            transactionHistories.add(eachTransactionHistory);
        }

        assertEquals(3, transactionHistories.size());
    }

    @Test
    @DisplayName(value = "Get Transaction History Count")
    public void getTransactionHistoryCount() {
        Iterable<TransactionHistory> iterableTransactionHistory = transactionHistoryRepository.findAll();

        List<TransactionHistory> transactionHistories = new ArrayList<>();
        for (TransactionHistory transactionHistory : iterableTransactionHistory) {
            transactionHistories.add(transactionHistory);
        }

        assertEquals(2, transactionHistories.size());
    }

    @Test
    @DisplayName(value = "Get All Transaction History By memberId")
    public void getAllTransactionHistoryByMemberId() {
        Long memberId = 1L;
        List<TransactionHistory> transactionHistories = transactionHistoryRepository.findAllByMemberId(memberId);

        assertEquals(1, transactionHistories.size());
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteAccount);
        jdbc.execute(sqlDeleteTransactionHistory);
        jdbc.execute(sqlDeleteMember);
    }
}
