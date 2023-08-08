package com.exercise.bank.data.repository;

import com.exercise.bank.data.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findByUsername(String username);
}
