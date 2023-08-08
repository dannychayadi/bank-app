package com.exercise.bank.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {


    @GetMapping("/")
    public String redirectHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getIndex(Model model, HttpServletRequest hsv) {
        model.addAttribute("username", hsv.getUserPrincipal().getName());
        model.addAttribute("title", "Home");
        return "home/index";
    }
}
