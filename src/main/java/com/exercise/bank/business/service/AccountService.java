package com.exercise.bank.business.service;

import com.exercise.bank.data.model.Account;
import com.exercise.bank.data.model.Member;
import com.exercise.bank.data.model.TransactionHistory;
import com.exercise.bank.data.repository.AccountRepository;
import com.exercise.bank.data.repository.MemberRepository;
import com.exercise.bank.data.repository.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    private MemberRepository memberRepository;

    private TransactionHistoryRepository transactionHistoryRepository;

    public AccountService(AccountRepository accountRepository,
                          MemberRepository memberRepository,
                          TransactionHistoryRepository transactionHistoryRepository) {
        this.accountRepository = accountRepository;
        this.memberRepository = memberRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Transactional
    public Boolean transferBalance(Member member,
                                   String amount,
                                   String recipientAccountNumber) {
        if (amount.isEmpty() || recipientAccountNumber.isEmpty()) {
            return false;
        }

        // get member balance
        String balance = member.getAccount().getBalance();

        // get recipient account balance
        Account recipientAccount = accountRepository.findByAccountNumber(recipientAccountNumber);

        if (recipientAccount == null) {
            return false;
        }

        BigDecimal amountbde = new BigDecimal(amount);
        BigDecimal balancebde = new BigDecimal(balance);
        BigDecimal recipientBalancebde = new BigDecimal(recipientAccount.getBalance());

        Boolean result = validateTransferValue(amount, recipientAccountNumber, amountbde, balancebde, member);
        if (result == false) {
            return result;
        }

        // substract balance
        BigDecimal substractBalancebde = balancebde.subtract(amountbde);

        // add balance
        BigDecimal addBalancebde = recipientBalancebde.add(amountbde);

        // add balance to model
        recipientAccount.setBalance(addBalancebde.toString());
        member.getAccount().setBalance(substractBalancebde.toString());

        // create transaction history object model
        TransactionHistory transactionHistory = new TransactionHistory(
                                                                amountbde.toString(),
                                                                recipientAccount.getAccountNumber(),
                                                                "Transfer",
                                                                LocalDateTime.now());
        transactionHistory.setMember(member);

        // save
        memberRepository.save(member);
        accountRepository.save(recipientAccount);
        transactionHistoryRepository.save(transactionHistory);

        return true;
    }

    private static Boolean validateTransferValue(String amount,
                                                 String recipientAccountNumber,
                                                 BigDecimal amountbde,
                                                 BigDecimal balancebde,
                                                 Member member) {
        if (amountbde.longValue() == 0 || amountbde.longValue() < 0) {
            return false;
        }

        if (balancebde.longValue() < amountbde.longValue()) {
            return false;
        }

        if (member.getAccount().getAccountNumber().equals(recipientAccountNumber)) {
            return false;
        }

        return true;
    }
}
