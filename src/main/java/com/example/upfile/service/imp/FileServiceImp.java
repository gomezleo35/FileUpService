package com.example.upfile.service.imp;

import com.example.upfile.model.entity.File;
import com.example.upfile.repository.FileRepository;
import com.example.upfile.service.FileService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImp implements FileService {

    @Autowired
    FileRepository repository;

    @Override
    public File save(MultipartFile file, String hash) throws Exception {
        Date date = Date.from(java.time.ZonedDateTime.now().toInstant());

        if(hash.equals("SHA-256")){

            String sha256 = Hashing.sha256().hashBytes(file.getBytes()).toString();
            final Optional<File> file1 = repository.findByHash256(sha256);

            if( file1.isPresent()) {
                repository.updateByid(date,file1.get().getId());
            } else {
                File obj = File.builder()
                        .fileName(file.getOriginalFilename())
                        .hash256(sha256)
                        .build();
                repository.save(obj);
            }
            return this.findByHash(sha256,hash).get();

        }else{
            String sha512 = Hashing.sha512().hashBytes(file.getBytes()).toString();
            final Optional<File> file1 = repository.findByHash512(sha512);

            if( file1.isPresent() ) {
                repository.updateByid(date,file1.get().getId());
            }else{
                File obj = File.builder()
                        .fileName(file.getOriginalFilename())
                        .hash512(sha512)
                        .build();
                repository.save(obj);
            }
            return this.findByHash(sha512, hash).get();
        }
    }

    @Override
    public List<File> save(List<MultipartFile> files, String hash) throws Exception {
         List<File> listResponse = new ArrayList<>();

        for(MultipartFile file : files){
            final File document = this.save(file, hash);
            listResponse.add(document);
        }
        return listResponse;
    }

    @Override
    public List<File> findAll() {
        final List<File> listRepository = repository.findAll();
        List<File> listResponse = new ArrayList<>();

        listRepository.forEach( x -> listResponse.add(x));

        return listResponse;
    }

    @Override
    public Optional<File> findByHash(String hashId, String hashType) {

        if (hashType.equals("SHA-256")){
            return  repository.findByHash256(hashId);
        }

        return repository.findByHash512(hashId);
    }

}
