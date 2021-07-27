package com.study.security.global;

import com.study.security.auth.annotation.LoginUser;
import com.study.security.auth.dto.SessionUser;
import com.study.security.domain.member.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping({"/", "/index"})
    public String index(ModelMap model, @LoginUser SessionUser sessionUser){
        /*SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        * -> annotaion방식으로 변경
        * */
        model.addAttribute("userName", sessionUser.getName());
        return "index";
    }
}
