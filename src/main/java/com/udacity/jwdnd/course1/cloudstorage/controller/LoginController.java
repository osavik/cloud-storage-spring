package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    //Note: we don't have PostMapping(), because we handle login process inside AuthenticationService

    @GetMapping()
    public String loginView(){
        return "login";
    }
}
