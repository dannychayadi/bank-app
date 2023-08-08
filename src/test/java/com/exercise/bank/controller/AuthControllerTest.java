package com.exercise.bank.controller;

import com.exercise.bank.BankApplication;
import com.exercise.bank.business.service.MemberService;
import com.exercise.bank.data.model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
public class AuthControllerTest {

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private UserModel userModel;

    @Autowired
    private MemberService memberService;

    @Mock
    private MemberService memberServiceMock;

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
    @DisplayName(value = "Get Login Http Request Before Login")
    public void getLoginHttpRequestBeforeLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/auth/login"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "auth/login");
    }

    @Test
    @WithMockUser
    @DisplayName(value = "Get Login Http Request After Login")
    public void getLoginHttpRequestAfterLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/auth/login"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName(value = "Get Register Http Request Before Login")
    public void getRegisterHttpRequestBeforeLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/auth/register"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "register");
    }

    @Test
    @WithMockUser
    @DisplayName(value = "Get Register Http Request After Login")
    public void getRegisterHttpRequestAfterLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/auth/register"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName(value = "Add Member Http Request")
    public void addMemberHttpRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .param("username", "userdemo2")
                        .param("password", "{bcrypt}" + passwordEncoder().encode("userdemo456"))
                        .param("firstName", "User")
                        .param("lastName", "Demo Two")
                        .param("email", "user@demo2.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/register?success"))
                .andReturn();
    }

    @Test
    @WithMockUser
    @DisplayName(value = "Get Logout Http Request")
    public void getLogoutHttpRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/logout")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login?logout"))
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
