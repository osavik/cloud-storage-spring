package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping()
    public String showHomePage(){
        return "home";
    }

    @PostMapping("/uploadfile")
    public String uploadFile(Model model){
        System.out.println("inside upload file");

        return "home";
    }

}
