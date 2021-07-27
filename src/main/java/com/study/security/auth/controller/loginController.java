package com.study.security.auth.controller;

import com.study.security.domain.member.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class loginController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }
}
