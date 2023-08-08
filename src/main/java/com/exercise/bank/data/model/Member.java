package com.exercise.bank.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Role role;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH})
    private List<TransactionHistory> transactionHistoryList;

    public Member() {
    }

    public Member(Long id, String username, String password, String firstName, String lastName, String email, LocalDateTime createdDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdDate = createdDate;
    }

    public Member(String username, String password, String firstName, String lastName, String email, LocalDateTime createdDate, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdDate = createdDate;
        this.role = role;
    }

    public Member(Long id, String username, String password, String firstName, String lastName, String email, LocalDateTime createdDate, Role role, Account account) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdDate = createdDate;
        this.role = role;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<TransactionHistory> getTransactionHistoryList() {
        return transactionHistoryList;
    }

    public void setTransactionHistoryList(List<TransactionHistory> transactionHistoryList) {
        this.transactionHistoryList = transactionHistoryList;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", role=" + role +
                ", account=" + account +
                '}';
    }
}
