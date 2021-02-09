package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFiles(Integer userId){
        return fileMapper.getFilesByUserId(userId);
    }

    public int saveFile(MultipartFile file, int userId) throws IOException {
        // parse MultipartFile to File
        File fileToSave = new File(null, file.getOriginalFilename(), file.getContentType(),String.valueOf(file.getSize()),userId, file.getBytes());

        return fileMapper.insertFile(fileToSave);
    }

    public File getFileByUserIdAndFilename(Integer userId, String filename){
        return fileMapper.getFileByNameAndUserId(userId, filename);
    }

    public File getFileByUserIdAndFileId(Integer userId, Integer fileId){
        return fileMapper.getFileByFileIdAndUserId(userId, fileId);
    }

    public boolean isFileNameUniqueForUser(Integer userId, String filename){
        return getFileByUserIdAndFilename(userId, filename) == null;
    }

    public void deleteFileByFileIdAndUserId(Integer userId, Integer fileId){
        fileMapper.deleteFileByFileIdAndUserId(userId, fileId);
    }


}
