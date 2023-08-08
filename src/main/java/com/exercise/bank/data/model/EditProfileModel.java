package com.exercise.bank.data.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class EditProfileModel {

    @NotEmpty(message = "first name is required")
    private String firstName;

    @NotEmpty(message = "last name is required")
    private String lastName;

    @Email(message = "email must be valid")
    @NotEmpty(message = "email is required")
    private String email;

    public EditProfileModel() {
    }

    public EditProfileModel(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
