package com.campus.myapp;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    
    //@RequestMapping(value = "/", method = RequestMethod.GET)
	@RequestMapping("/")
    public String home(Locale locale, Model model) {
        //Model은 뷰페이지로 보내는 데이터를 셋팅 할 수 있다.
        model.addAttribute("msg","처음 실행하는 스프링");
        return "home";
    }
    
}