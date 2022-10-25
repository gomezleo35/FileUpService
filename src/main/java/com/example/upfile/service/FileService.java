package com.example.upfile.service;

import com.example.upfile.model.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {

    File save(MultipartFile file, String hash) throws Exception;
    List<File> save(List<MultipartFile> files, String hash) throws Exception;
    List<File> findAll();
    Optional<File> findByHash(String hashId, String hashType);
}
