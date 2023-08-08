package com.exercise.bank.business.service;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class TransactionHistoryServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

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
    @DisplayName(value = "Get All Transaction History Of One Member")
    public void getAllTransactionHistoryOfOneMember() {
        Member member = memberService.findByUsername("userdemo");
        assertNotNull(member);

        List<TransactionHistory> transactionHistories = transactionHistoryService.getTransactionHistoryByMemberId(member.getId());
        member.setTransactionHistoryList(transactionHistories);

        assertEquals(1, member.getTransactionHistoryList().size());
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteAccount);
        jdbc.execute(sqlDeleteTransactionHistory);
        jdbc.execute(sqlDeleteMember);
    }
}
