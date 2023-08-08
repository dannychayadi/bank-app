package com.exercise.bank.data.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class TransferModel {

    @NotEmpty(message = "recipient account number is required")
    @Length(min = 10, max = 10, message = "character must be 10")
    @Digits(integer = 10, fraction = 0, message = "must be 10 digit number")
    private String recipientAccountNumber;

    @NotEmpty(message = "amount is required")
    @Min(value = 1, message = "must equal to or more than 1")
    @Digits(integer = 10, fraction = 2, message = "cent must be not more than 2 digit")
    private String amount;


    public TransferModel() {

    }

    public TransferModel(String recipientAccountNumber, String amount) {
        this.recipientAccountNumber = recipientAccountNumber;
        this.amount = amount;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
