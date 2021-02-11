package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Form.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentialByUserId(Integer userId){
        return credentialMapper.getAllCredentials(userId);
    }

    public int saveCredential(Integer userId, CredentialForm credentialForm){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);


        Credential credential = new Credential(null, credentialForm.getUrl(),
                credentialForm.getUsername(), encodedKey, encryptedPassword, userId);

        return credentialMapper.insertCredential(credential);
    }

    public void deleteCredential(Integer userId, Integer credentialId){
        credentialMapper.deleteCredentialByCredentialIdAndUserId(userId, credentialId);
    }

    public int updateCredentialByUserIdAndCredentialId(Integer userId, CredentialForm credentialForm){


        Credential credential = null;

        return credentialMapper.updateCredentialByCredentialIdAndUserId(userId, credential.getCredentialId(),
                credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword());
    }



}
