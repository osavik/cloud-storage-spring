package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.service.facade.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private CredentialService credentialService;
    private NoteService noteService;
    private UserService userService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    public HomeController(FileService fileService, CredentialService credentialService,
                          NoteService noteService, UserService userService){
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping()
    public String showHomePage(){
        return "home";
    }

    // TODO handle file size limit
    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model){
        boolean successfulUpload = false;

        // session is expired: go back to login page
        User user = userService.getLoggedInUser();
        if(user == null){
            return "login";
        }

        if (file.isEmpty()){
            model.addAttribute("file_upload_hint", true);
            return "home";
        }

        if (!fileService.isFileNameUniqueForUser(user.getUserId(), file.getOriginalFilename())){
            model.addAttribute("file_upload_error_name", true);
        }else{
            try {
                int rowsAdded = fileService.saveFile(file, user.getUserId());
                if (rowsAdded > 0){ // file was inserted in DB
                    successfulUpload = true;
                }
            } catch (Exception e) {
                model.addAttribute("file_upload_error", true);
                e.printStackTrace();
            }
        }

        model.addAttribute("file_upload_successful", successfulUpload);

        return "home";
    }

}
