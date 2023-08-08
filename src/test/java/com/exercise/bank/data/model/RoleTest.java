package com.exercise.bank.data.model;

import com.exercise.bank.BankApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
public class RoleTest {

    @Autowired
    private Role role;

    @BeforeEach
    public void beforeEach() {
        role = new Role("ROLE_USER");
    }

    @Test
    @DisplayName(value = "Assert Model Not Null")
    public void assertModelNotNull() {
        assertNotNull(role);
    }

    @Test
    @DisplayName(value = "Assert Setter")
    public void assertSetter() {
        role.setRole("ROLE_MEMBER");

        assertEquals("ROLE_MEMBER", role.getRole());
        assertNotEquals("ROLE_USER", role.getRole());
    }

    @AfterEach
    public void afterEach() {

    }
}
