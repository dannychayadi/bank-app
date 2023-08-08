package com.exercise.bank.business.service;

import com.exercise.bank.data.model.TransactionHistory;
import com.exercise.bank.data.repository.TransactionHistoryRepository;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryService {

    private TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public List<TransactionHistory> getTransactionHistoryByMemberId(Long memberId) {
        List<TransactionHistory> transactionHistories = transactionHistoryRepository.findAllByMemberId(memberId);
        return transactionHistories;
    }

    public Page<TransactionHistory> getTransactionHistoryByMemberIdPageable(Long memberId, Pageable pageable) {
        return transactionHistoryRepository.findAllByMemberIdOrderByCreatedDateDesc(memberId, pageable);
    }
}
