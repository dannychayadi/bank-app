package com.exercise.bank.data.repository;

import com.exercise.bank.data.model.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long>, PagingAndSortingRepository<TransactionHistory, Long> {

    List<TransactionHistory> findAllByMemberId(Long memberId);

    Page<TransactionHistory> findAllByMemberIdOrderByCreatedDateDesc(Long memberId, Pageable pageable);
}
