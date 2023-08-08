package com.exercise.bank.business.service;

import com.exercise.bank.BankApplication;
import com.exercise.bank.data.model.*;
import com.exercise.bank.data.repository.MemberRepository;
import com.exercise.bank.data.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class MemberServiceTest {

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private Member member;

    @Autowired
    private UserModel userModel;

    @Autowired
    private EditProfileModel editProfileModel;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Value(value = "${sql.script.create.member}")
    public String sqlCreateMember;

    @Value(value = "${sql.script.create.roles}")
    public String sqlCreateRoles;

    @Value(value = "${sql.script.create.account}")
    public String sqlCreateAccount;

    @Value(value = "${sql.script.delete.member}")
    public String sqlDeleteMember;

    @Value(value = "${sql.script.delete.roles}")
    public String sqlDeleteRoles;

    @Value(value = "${sql.script.delete.account}")
    public String sqlDeleteAccount;

    @Value(value = "${sql.script.delete.transaction_history}")
    public String sqlDeleteTransactionHistory;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateMember);
        jdbc.execute(sqlCreateRoles);
        jdbc.execute(sqlCreateAccount);
    }

    @Test
    @DisplayName(value = "Add Member")
    public void addMember() {
        userModel = new UserModel(
                "userdemo2",
                "{bcrypt}"+passwordEncoder().encode("userdemo456"),
                "User",
                "Demo Two",
                "user@demo2.com");

        memberService.save(userModel);

        Member memberFound = memberRepository.findByUsername("userdemo2");

        assertEquals("userdemo2", memberFound.getUsername());
        assertNotNull(memberFound.getRole());
        assertNotNull(memberFound.getAccount());

        List<TransactionHistory> transactionHistories = transactionHistoryRepository.findAllByMemberId(memberFound.getId());
        assertEquals(1, transactionHistories.size());
    }

    @Test
    @DisplayName(value = "Update Member")
    public void updateMember() {
        String username = "userdemo";

        editProfileModel = new EditProfileModel();
        editProfileModel.setFirstName("New User");
        editProfileModel.setLastName("updated Demo one");
        editProfileModel.setEmail("user1@demo2.com");

        memberService.update(editProfileModel, username);

        Member memberFound = memberService.findByUsername(username);

        assertEquals("New User", memberFound.getFirstName());
        assertNotEquals("User", memberFound.getFirstName());

        assertEquals("updated Demo one", memberFound.getLastName());
        assertNotEquals("Demo One", memberFound.getLastName());

        assertEquals("user1@demo2.com", memberFound.getEmail());
        assertNotEquals("user@demo.com", memberFound.getEmail());
    }

    @Test
    @DisplayName(value = "Get Invalid Member Return Null")
    public void getInvalidMemberReturnNull() {
        Member memberFound = memberService.findByUsername("userdemo7");

        assertNull(memberFound);
    }

    @Test
    @DisplayName(value = "Get Valid Member")
    public void getValidMember() {
        Member memberFound = memberService.findByUsername("userdemo");

        assertNotNull(memberFound);
        assertNotNull(memberFound.getRole());
        assertNotNull(memberFound.getAccount());
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteAccount);
        jdbc.execute(sqlDeleteRoles);
        jdbc.execute(sqlDeleteTransactionHistory);
        jdbc.execute(sqlDeleteMember);
    }
}
