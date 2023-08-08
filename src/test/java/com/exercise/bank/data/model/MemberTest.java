package com.exercise.bank.data.model;

import com.exercise.bank.BankApplication;
import com.exercise.bank.business.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = BankApplication.class)
public class MemberTest {

    @Autowired
    private Member member;

    @BeforeEach
    public void beforeEach() {
        member = new Member(
                1L,
                "userdemo",
                "userdemo123",
                "User",
                "Demo One",
                "user@demo.com",
                LocalDateTime.of(2012, 01, 02, 13, 30));
    }

    @Test
    @DisplayName(value = "Assert Equal toString Method")
    public void assertEqualToStringMethod() {

        assertEquals("Member{" +
                        "id=1, " +
                        "username='userdemo', " +
                        "password='userdemo123', " +
                        "firstName='User', " +
                        "lastName='Demo One', " +
                        "email='user@demo.com', " +
                        "createdDate=2012-01-02T13:30, " +
                        "role=null, " +
                        "account=null" +
                        "}",
                member.toString());
    }

    @Test
    @DisplayName(value = "Assert Setter")
    public void assertSetters() {

        member.setUsername("userdemo2");
        member.setLastName("Demo");

        assertEquals("userdemo2", member.getUsername());
        assertNotEquals("userdemo", member.getUsername());

        assertEquals("Demo", member.getLastName());
        assertNotEquals("Demo2", member.getLastName());
    }
}
