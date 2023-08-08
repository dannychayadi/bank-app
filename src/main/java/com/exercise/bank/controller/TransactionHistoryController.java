package com.exercise.bank.controller;

import com.exercise.bank.business.service.MemberService;
import com.exercise.bank.business.service.TransactionHistoryService;
import com.exercise.bank.data.model.Member;
import com.exercise.bank.data.model.TransactionHistory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/history")
public class TransactionHistoryController {

    private TransactionHistoryService transactionHistoryService;

    private MemberService memberService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService,
                                        MemberService memberService) {
        this.transactionHistoryService = transactionHistoryService;
        this.memberService = memberService;
    }

    @GetMapping("")
    public String getHistory(Model model, HttpServletRequest hsv, @PageableDefault(size = 5) Pageable page) {
        String username = hsv.getUserPrincipal().getName();

        Member member = memberService.findByUsername(username);
        Page<TransactionHistory> transactionHistories = transactionHistoryService.getTransactionHistoryByMemberIdPageable(member.getId(), page);

        model.addAttribute("transactionHistories", transactionHistories);
        model.addAttribute("title", "History");

        return "transaction-history/history";
    }

}
