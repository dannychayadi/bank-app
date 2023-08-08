package com.exercise.bank.business.service;

import com.exercise.bank.data.model.*;
import com.exercise.bank.data.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void save(UserModel user) {
        Member addMember = new Member();
        addMember.setUsername(user.getUsername());
        addMember.setPassword("{bcrypt}"+passwordEncoder().encode(user.getPassword()));
        addMember.setFirstName(user.getFirstName());
        addMember.setLastName(user.getLastName());
        addMember.setEmail(user.getEmail());
        addMember.setCreatedDate(LocalDateTime.now());

        Role addRole = new Role("ROLE_MEMBER", addMember);
        addMember.setRole(addRole);

        // create random number
        Random random = new Random();
        Long randomNumber = random.nextLong(90000000) + 10000000;
        String accountNumber = "82" + randomNumber;

        Account addAccount = new Account("100.00", accountNumber, LocalDateTime.now(), addMember);
        addMember.setAccount(addAccount);

        // create transaction history
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();

        TransactionHistory transactionHistory = new TransactionHistory(
                                                            "100.00",
                                                            "",
                                                            "Deposit",
                                                            LocalDateTime.now());
        transactionHistory.setMember(addMember);
        transactionHistoryList.add(transactionHistory);
        addMember.setTransactionHistoryList(transactionHistoryList);

        memberRepository.save(addMember);
    }

    public void update(EditProfileModel editProfile, String username) {
        Member member = memberRepository.findByUsername(username);

        member.setFirstName(editProfile.getFirstName());
        member.setLastName(editProfile.getLastName());
        member.setEmail(editProfile.getEmail());

        memberRepository.save(member);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
