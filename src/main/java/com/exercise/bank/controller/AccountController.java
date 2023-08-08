package com.exercise.bank.controller;

import com.exercise.bank.business.service.AccountService;
import com.exercise.bank.business.service.MemberService;
import com.exercise.bank.data.model.Account;
import com.exercise.bank.data.model.Member;
import com.exercise.bank.data.model.TransferModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping
public class AccountController {

    private MemberService memberService;

    private AccountService accountService;

    public AccountController(MemberService memberService,
                             AccountService accountService) {
        this.memberService = memberService;
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public String balance(Model model, HttpServletRequest hsv) {
        Member member = memberService.findByUsername(hsv.getUserPrincipal().getName());
        String accountNumber = member.getAccount().getAccountNumber();
        String balance = member.getAccount().getBalance();

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.UK);

        model.addAttribute("balanceModified", nf.format(new BigDecimal(balance)));
        model.addAttribute("accountNumber", accountNumber);
        model.addAttribute("title", "Balance");

        return "account/balance";
    }

    @GetMapping("/transfer")
    public String transfer(Model model) {
        model.addAttribute("transferModel", new TransferModel());
        model.addAttribute("title", "Transfer");

        return "account/transfer";
    }

    @PostMapping("/transfer")
    public String postTransfer(HttpServletRequest hsv,
                               Model model,
                               @Valid @ModelAttribute("transferModel") TransferModel transferModel,
                               Errors errors) {
        if (!errors.hasErrors()) {
            Member member = memberService.findByUsername(hsv.getUserPrincipal().getName());
            Boolean result = accountService.transferBalance(member, transferModel.getAmount(), transferModel.getRecipientAccountNumber());

            if (!result) {
                return "redirect:/transfer?error";
            }

            return "redirect:/transfer?success";
        }

        model.addAttribute("title", "Transfer");
        return "account/transfer";
    }
}
