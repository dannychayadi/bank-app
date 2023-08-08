package com.exercise.bank.controller;

import com.exercise.bank.business.service.MemberService;
import com.exercise.bank.data.model.EditProfileModel;
import com.exercise.bank.data.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private MemberService memberService;

    public ProfileController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String profile(Model model, HttpServletRequest hsv) {
        String username = hsv.getUserPrincipal().getName();
        Member member = memberService.findByUsername(username);

        EditProfileModel editProfileModel = new EditProfileModel(
                                                    member.getFirstName(), member.getLastName(),
                                                    member.getEmail());

        model.addAttribute("editProfile", editProfileModel);
        model.addAttribute("title", "Profile");
        return "user/profile";
    }

    @PostMapping
    public String postProfile(HttpServletRequest hsv,
                              @Valid @ModelAttribute("editProfile") EditProfileModel editProfile,
                              Errors errors,
                              Model model) {
        if (!errors.hasErrors()) {
            memberService.update(editProfile, hsv.getUserPrincipal().getName());
            return "redirect:/profile?success";
        }
        model.addAttribute("title", "Profile");
        return "user/profile";
    }
}
