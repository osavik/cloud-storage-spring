package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final FileService fileService;
    private final CredentialService credentialService;
    private final NoteService noteService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, CredentialService credentialService,
                          NoteService noteService, UserService userService, EncryptionService encryptionService){
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String showHomePage(NoteForm noteForm, CredentialForm credentialForm, Model model){

        User user = userService.getLoggedInUser();
        if(user == null){
            return "login";
        }

        if (model.getAttribute("activeTab") == null){
            model.addAttribute("activeTab", "files");
        }

        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));
        model.addAttribute("notes", noteService.getAllNotes(user.getUserId()));
        model.addAttribute("credentials", credentialService.getAllCredentialByUserId(user.getUserId()));

        return "home";
    }

    @PostMapping("/credential/save")
    public String saveCredential(CredentialForm credentialForm, RedirectAttributes redirectAttributes){
        User user = userService.getLoggedInUser();

        if(credentialForm.getCredentialId() != null){
            credentialService.updateCredentialByUserIdAndCredentialId(user.getUserId(), credentialForm);
            redirectAttributes.addFlashAttribute("cred_edit_hint", true);
        }else {
            credentialService.saveCredential(user.getUserId(), credentialForm);
            redirectAttributes.addFlashAttribute("cred_create_hint", true);
        }

        redirectAttributes.addFlashAttribute("activeTab", "creds");

        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable String credentialId,
                                   RedirectAttributes redirectAttributes){
        User user = userService.getLoggedInUser();

        credentialService.deleteCredential(user.getUserId(), Integer.valueOf(credentialId));

        redirectAttributes.addFlashAttribute("activeTab", "creds");
        redirectAttributes.addFlashAttribute("cred_delete_hint", true);

        return "redirect:/home";
    }

    @PostMapping("/note/save")
    public String saveNote(NoteForm noteForm, RedirectAttributes redirectAttributes){
        User user = userService.getLoggedInUser();

        if (noteForm.getNoteId()!= null){
            noteService.updateNoteByUserIdAndNoteId(user.getUserId(), noteForm);
            redirectAttributes.addFlashAttribute("note_edit_hint", true);
        }else{
            noteService.saveNote(user.getUserId(), noteForm);
            redirectAttributes.addFlashAttribute("note_create_hint", true);
        }

        redirectAttributes.addFlashAttribute("activeTab", "notes");

        return "redirect:/home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable String noteId, NoteForm noteForm, RedirectAttributes redirectAttributes){
        User user = userService.getLoggedInUser();

        noteService.deleteNoteByUserIdAndNoteId(user.getUserId(), Integer.valueOf(noteId));

        redirectAttributes.addFlashAttribute("activeTab", "notes");
        redirectAttributes.addFlashAttribute("note_delete_hint", true);

        return "redirect:/home";
    }


    // TODO handle file size limit
    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes, NoteForm noteForm){
        boolean successfulUpload = false;

        // session is expired: go back to login page
        User user = userService.getLoggedInUser();
        if(user == null){
            return "login";
        }

        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("file_upload_hint", true);
        }else{
            if (!fileService.isFileNameUniqueForUser(user.getUserId(), file.getOriginalFilename())){
                redirectAttributes.addFlashAttribute("file_upload_error_name", true);
            }else{
                try {
                    int rowsAdded = fileService.saveFile(file, user.getUserId());
                    if (rowsAdded > 0){ // file was inserted in DB
                        successfulUpload = true;
                    }
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("file_upload_error", true);
                    e.printStackTrace();
                }
            }
        }

        redirectAttributes.addFlashAttribute("file_upload_successful", successfulUpload);
        redirectAttributes.addFlashAttribute("activeTab", "files");

        return "redirect:/home";
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable String fileId, RedirectAttributes redirectAttributes){
        User user = userService.getLoggedInUser();

        fileService.deleteFileByFileIdAndUserId(user.getUserId(), Integer.valueOf(fileId));
        redirectAttributes.addFlashAttribute("file_delete_hint", true);

        return "redirect:/home";
    }


    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileId){
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
