package com.exercise.bank.controller;

import com.exercise.bank.BankApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Value(value = "${sql.script.create.member}")
    public String sqlCreateMember;

    @Value(value = "${sql.script.create.roles}")
    public String sqlCreateRoles;

    @Value(value = "${sql.script.create.account}")
    public String sqlCreateAccount;

    @Value(value = "${sql.script.create.transaction_history2}")
    public String sqlCreateTransactionHistory2;

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
        jdbc.execute(sqlCreateTransactionHistory2);
    }

    @Test
    @DisplayName(value = "Get Profile Http Request Before Login")
    public void getProfileHttpRequestBeforeLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userdemo", password = "userdemo123", roles = "MEMBER")
    @DisplayName(value = "Get Profile Http Request After Login")
    public void getProfileHttpRequestAfterLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "user/profile");
    }

    @Test
    @WithMockUser(username = "userdemo", password = "userdemo123", roles = "MEMBER")
    @DisplayName(value = "Edit Profile Http Request After Login")
    public void editProfileHttpRequestAfterLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/profile")
                        .param("firstName", "New User")
                        .param("lastName", "Demo")
                        .param("email", "user@demo2.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?success"))
                .andReturn();
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteAccount);
        jdbc.execute(sqlDeleteRoles);
        jdbc.execute(sqlDeleteTransactionHistory);
        jdbc.execute(sqlDeleteMember);
    }
}
