package com.exercise.bank.data.model;

import com.exercise.bank.BankApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = BankApplication.class)
public class UserModelTest {

    @Autowired
    private UserModel userModel;

    @BeforeEach
    public void beforeEach() {
        userModel = new UserModel(
                "userdemo",
                "userdemo123",
                "User",
                "Demo One",
                "user@demo.com");
    }

    @Test
    @DisplayName(value = "Assert Equal toString Method")
    public void assertEqualToStringMethod() {

        assertEquals(
                "UserModel{" +
                        "username='userdemo', " +
                        "password='userdemo123', " +
                        "firstName='User', " +
                        "lastName='Demo One', " +
                        "email='user@demo.com'" +
                        "}",
                userModel.toString());
    }

    @Test
    @DisplayName(value = "Assert Setter")
    public void assertSetters() {

        userModel.setUsername("userdemo2");
        userModel.setLastName("Demo Two");
        userModel.setEmail("user2@demo.com");

        assertEquals("userdemo2", userModel.getUsername());
        assertNotEquals("userdemo", userModel.getUsername());

        assertEquals("Demo Two", userModel.getLastName());
        assertNotEquals("Demo", userModel.getLastName());

        assertEquals("user2@demo.com", userModel.getEmail());
        assertNotEquals("user@demo.com", userModel.getEmail());
    }
}
