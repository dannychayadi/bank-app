package com.exercise.bank.controller;

import com.exercise.bank.BankApplication;
import com.exercise.bank.data.model.Account;
import com.exercise.bank.data.repository.AccountRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(classes = BankApplication.class)
@TestPropertySource("/application-test.properties")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private AccountRepository accountRepository;

    @Value(value = "${sql.script.create.member}")
    public String sqlCreateMember;

    @Value(value = "${sql.script.create.roles}")
    public String sqlCreateRoles;

    @Value(value = "${sql.script.create.account}")
    public String sqlCreateAccount;

    @Value(value = "${sql.script.create.member2}")
    public String sqlCreateMember2;

    @Value(value = "${sql.script.create.roles2}")
    public String sqlCreateRoles2;

    @Value(value = "${sql.script.create.account2}")
    public String sqlCreateAccount2;

    @Value(value = "${sql.script.balance.account.add}")
    public String sqlAddBalanceAccount;

    @Value(value = "${sql.script.balance.account2.add}")
    public String sqlAddBalanceAccount2;

    @Value(value = "${sql.script.create.transaction_history}")
    public String sqlCreateTransactionHistory;

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
        jdbc.execute(sqlCreateMember2);
        jdbc.execute(sqlCreateRoles2);
        jdbc.execute(sqlCreateAccount2);
        jdbc.execute(sqlAddBalanceAccount);
        jdbc.execute(sqlAddBalanceAccount2);
        jdbc.execute(sqlCreateTransactionHistory);
        jdbc.execute(sqlCreateTransactionHistory2);
    }

    @Test
    @DisplayName(value = "Get Balance Http Request Before Login")
    public void getBalanceHttpRequestBeforeLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/balance"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userdemo", password = "userdemo123", roles = "MEMBER")
    @DisplayName(value = "Get Balance Http Request After Login")
    public void getBalanceHttpRequestAfterLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/balance"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "account/balance");
    }

    @Test
    @WithMockUser(username = "userdemo", password = "userdemo123", roles = "MEMBER")
    @DisplayName(value = "Get Transfer Http Request After Login")
    public void getTransferHttpRequestAfterLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/transfer"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "account/transfer");
    }

    @Test
    @WithMockUser(username = "userdemo", password = "userdemo123", roles = "MEMBER")
    @DisplayName(value = "Transfer Balance Http Request After Login")
    public void transferBalanceHttpRequestAfterLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
                        .param("amount", "10.00")
                        .param("recipientAccountNumber", "0139485435")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transfer?success"))
                .andReturn();

        Account account = accountRepository.findByAccountNumber("0139485435");
        assertEquals("110.00", account.getBalance());
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteAccount);
        jdbc.execute(sqlDeleteRoles);
        jdbc.execute(sqlDeleteTransactionHistory);
        jdbc.execute(sqlDeleteMember);
    }
}
