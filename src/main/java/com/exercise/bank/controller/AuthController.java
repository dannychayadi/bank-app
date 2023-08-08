package com.exercise.bank.controller;

import com.exercise.bank.business.service.MemberService;

import com.exercise.bank.data.model.UserModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("title", "Login");
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserModel());
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("user") UserModel user,
                               Errors errors,
                               Model model) {
        if (!errors.hasErrors()) {
            memberService.save(user);
            return "redirect:/auth/register?success";
        }

        model.addAttribute("title", "Register");
        return "register";
    }
}
