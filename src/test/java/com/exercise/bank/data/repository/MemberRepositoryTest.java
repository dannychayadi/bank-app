package com.exercise.bank.data.repository;

import com.exercise.bank.BankApplication;
import com.exercise.bank.data.model.Member;
import com.exercise.bank.data.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class MemberRepositoryTest {

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private Member member;

    @Autowired
    private JdbcTemplate jdbc;

    @Value(value = "${sql.script.create.member}")
    public String sqlCreateMember;

    @Value(value = "${sql.script.delete.member}")
    public String sqlDeleteMember;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateMember);
    }

    @Test
    @DisplayName(value = "Get Member Count")
    public void getMemberCount() {
        Iterable<Member> iterableMembers = memberRepository.findAll();

        List<Member> members = new ArrayList<>();

        for (Member member : iterableMembers) {
            members.add(member);
        }

        assertEquals(1, members.size());
    }

    @Test
    @DisplayName(value = "Create Member")
    public void createMember() {
        member = new Member(
                "userdemo2",
                "{bcrypt}"+passwordEncoder().encode("userdemo456"),
                "User",
                "Demo Two",
                "user@demo2.com",
                LocalDateTime.of(2012, 02, 03, 14, 45),
                new Role());

        member.setId(0L);
        memberRepository.save(member);

        Iterable<Member> iterableMembers = memberRepository.findAll();

        List<Member> members = new ArrayList<>();

        for (Member member : iterableMembers) {
            members.add(member);
        }

        assertEquals(2, members.size());
    }

    @Test
    @DisplayName(value = "Get One Member By Username")
    public void getOneMemberByUsername() {
        Member member = memberRepository.findByUsername("userdemo");

        assertNotNull(member);
        assertEquals("User", member.getFirstName());
        assertEquals("Demo One", member.getLastName());
    }

    @Test
    @DisplayName(value = "Get One Member By Username Return Null")
    public void getOneMemberByUsernameReturnNull() {
        Member member = memberRepository.findByUsername("userdemo10");
        assertNull(member);
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteMember);
    }
}
