package com.exercise.bank.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "balance")
    private String balance;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public Account() {
    }

    public Account(String balance, String accountNumber, LocalDateTime createdDate) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.createdDate = createdDate;
    }

    public Account(String balance, String accountNumber, LocalDateTime createdDate, Member member) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.createdDate = createdDate;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
        return "Account{" +
                "id=" + id +
                ", balance='" + balance + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
