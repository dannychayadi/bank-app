package com.exercise.bank.data.model;

import com.exercise.bank.BankApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
public class EditProfileModelTest {

    @Autowired
    private EditProfileModel editProfileModel;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    public void assertModelNotNull() {
        editProfileModel = new EditProfileModel();

        assertNotNull(editProfileModel);
    }

    @Test
    public void assertSetters() {
        editProfileModel = new EditProfileModel("User", "Demo 12", "user12@demo.com");

        editProfileModel.setLastName("Demo 12 updated");

        assertEquals("Demo 12 updated", editProfileModel.getLastName());
        assertNotEquals("Demo 12", editProfileModel.getLastName());
    }

    @AfterEach
    public void afterEach() {

    }

}
