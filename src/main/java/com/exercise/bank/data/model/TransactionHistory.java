package com.exercise.bank.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private String amount;

    @Column(name = "recipient_account_number")
    private String recipientAccountNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "member_id")
    private Member member;

    public TransactionHistory() {
    }

    public TransactionHistory(String amount, String recipientAccountNumber, String description, LocalDateTime createdDate) {
        this.amount = amount;
        this.recipientAccountNumber = recipientAccountNumber;
        this.description = description;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", recipientAccountNumber='" + recipientAccountNumber + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
