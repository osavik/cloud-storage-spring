package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/login")
public class LoginController {

    //Note: we don't have PostMapping(), because we handle login process inside AuthenticationService

    private final UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public String loginView(){
        // TODO delete it - just for testing purpose
        if (userService.isUsernameAvailable("vik")){
            User user  = new User(null, "vik", null, "vik", "vik", "vik");
            userService.createUser(user);
        }

        return "login";
    }
}
