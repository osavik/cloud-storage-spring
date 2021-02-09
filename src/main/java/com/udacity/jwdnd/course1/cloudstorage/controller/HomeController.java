package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {

    private final FileService fileService;
    private final CredentialService credentialService;
    private final NoteService noteService;
    private final UserService userService;

    public HomeController(FileService fileService, CredentialService credentialService,
                          NoteService noteService, UserService userService){
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model){

        User user = userService.getLoggedInUser();
        if(user == null){
            return "login";
        }

        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));

        return "home";
    }

    // TODO handle file size limit
    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model){
        boolean successfulUpload = false;

        // session is expired: go back to login page
        User user = userService.getLoggedInUser();
        if(user == null){
            return "login";
        }

        if (file.isEmpty()){
            model.addAttribute("file_upload_hint", true);
        }else{
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
        }

        model.addAttribute("file_upload_successful", successfulUpload);
        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));

        return "home";
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable String fileId){
        System.out.println("file id to delete = " + fileId);

        User user = userService.getLoggedInUser();
        fileService.deleteFileByFileIdAndUserId(user.getUserId(), Integer.valueOf(fileId));

        return "redirect:/home";
    }


    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileId){
        System.out.println("file id to view = " + fileId);

        User user = userService.getLoggedInUser();
        File file = fileService.getFileByUserIdAndFileId(user.getUserId(), Integer.valueOf(fileId));

        if (file != null){
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                            + file.getFileName() + "\"")
                    .body(new ByteArrayResource(file.getFileData()));
        }else{
            // TODO give user some hint etc
            return null;
        }
    }




}
