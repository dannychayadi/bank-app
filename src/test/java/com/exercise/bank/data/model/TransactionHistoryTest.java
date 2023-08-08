package com.exercise.bank.data.model;


import com.exercise.bank.BankApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = BankApplication.class)
public class TransactionHistoryTest {

    @Autowired
    private TransactionHistory transactionHistory1;

    @Autowired
    private TransactionHistory transactionHistory2;

    @BeforeEach
    public void beforeEach() {
        transactionHistory1 = new TransactionHistory();

        LocalDateTime createdDate = LocalDateTime.of(2012, 1, 2, 13, 5);
        transactionHistory2 = new TransactionHistory("10", "0123456789", "Transfer", createdDate);
    }

    @Test
    @DisplayName(value = "Assert Model Not Null")
    public void asserModelNotNull() {
        assertNotNull(transactionHistory1);
    }

    @Test
    @DisplayName(value = "Assert Second Model Not Null")
    public void assertSecondModelNotNull() {
        assertNotNull(transactionHistory2);
    }

    @Test
    @DisplayName(value = "Assert Equals Set And Get Id")
    public void assertEqualsSetAndGetId() {
        transactionHistory1.setId(1L);
        assertEquals(1L, transactionHistory1.getId());
    }

    @Test
    @DisplayName(value = "Assert Equals toString Method")
    public void assertEqualsToStringMethod() {
        assertEquals("TransactionHistory{" +
                        "id=null, " +
                        "amount='10', " +
                        "recipientAccountNumber='0123456789', " +
                        "description='Transfer', " +
                        "createdDate=2012-01-02T13:05" +
                        "}",
                transactionHistory2.toString());
    }

    @AfterEach
    public void afterEach() {

    }
}
