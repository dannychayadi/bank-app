package com.exercise.bank.data.model;

import com.exercise.bank.BankApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
public class TransferModelTest {

    @Autowired
    private TransferModel transferModel;

    @BeforeEach
    public void beforeEach() {
        transferModel = new TransferModel("1234567890", "2.00");
    }

    @Test
    @DisplayName(value = "Assert Model Not Null")
    public void assertModelNotNull() {
        assertNotNull(transferModel);
    }

    @Test
    @DisplayName(value = "Assert Setters")
    public void assertSetters() {

        transferModel.setAmount("3.00");
        transferModel.setRecipientAccountNumber("2373847129");

        assertEquals("3.00", transferModel.getAmount());
        assertNotEquals("2.00", transferModel.getAmount());

        assertEquals("2373847129", transferModel.getRecipientAccountNumber());
        assertNotEquals("1234567890", transferModel.getRecipientAccountNumber());
    }

    @AfterEach
    public void afterEach() {

    }
}
