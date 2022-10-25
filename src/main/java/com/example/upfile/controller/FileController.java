package com.example.upfile.controller;

import com.example.upfile.model.Error;
import com.example.upfile.model.entity.File;
import com.example.upfile.model.http.UpFileResponse;
import com.example.upfile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class FileController {


    @Autowired
    FileService service;

    @PostMapping("/hash/{hastType}")
    public ResponseEntity<UpFileResponse> uploadFiles(@RequestPart("files") List<MultipartFile> files,
                                                      @PathVariable String hastType) {
        if (hastType.equals("SHA-256") || hastType.equals("SHA-512")) {
            try {
                final List<File> documentList = service.save(files, hastType);
                return ResponseEntity.status(HttpStatus.CREATED).body(UpFileResponse.builder()
                        .algorithm(hastType)
                        .documents(documentList)
                        .build());
            } catch (Exception e) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
                Error error = Error.builder()
                        .status("500")
                        .path("/api/documents/hash")
                        .timestamp(timeStamp)
                        .message(e.getMessage())
                        .build();

                return ResponseEntity.internalServerError().body(
                        UpFileResponse.builder()
                                .error(error)
                                .build()
                );
            }
        } else {
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
            Error error = Error.builder()
                    .status("400")
                    .path("/api/documents/hash")
                    .timestamp(timeStamp)
                    .message("El parámetro ‘hash’ solo puede ser ‘SHA-256’ o ‘SHA-512’")
                    .build();
            return ResponseEntity.badRequest().body(
                    UpFileResponse.builder()
                            .error(error)
                            .build()
            );

    }

    }
    @GetMapping
    public  ResponseEntity<?> getAll(){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        try{
            final List<File> fileList = service.findAll();
            if(fileList.isEmpty()){
                Error error = Error.builder()
                        .status("404")
                        .path("/api/hash")
                        .timestamp(timeStamp)
                        .message("No hay documentos disponibles")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        UpFileResponse.builder()
                                .error(error)
                                .build()
                );
            }else{
                return ResponseEntity.ok(fileList);
            }
        }catch (Exception e){
            Error error = Error.builder()
                    .status("500")
                    .path("/api/hash")
                    .timestamp(timeStamp)
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(
                    UpFileResponse.builder()
                            .error(error)
                            .build()
            );

        }


    }

    @GetMapping("/{hashType}/{hash}")
    public ResponseEntity<?> getDocumentByHash(@PathVariable String hashType, @PathVariable String hash) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        if (hashType.equals("SHA-256") || hashType.equals("SHA-512")) {
            try {
                final Optional<File> file = service.findByHash(hash, hashType);
                if(file.isPresent()){
                    return ResponseEntity.ok(file.get());
                } else{
                    Error error = Error.builder()
                            .status("404")
                            .path("/api/hash")
                            .timestamp(timeStamp)
                            .message("No hay ningún documento con ese nombre")
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            UpFileResponse.builder()
                                    .error(error)
                                    .build()
                    );
                }



            } catch (Exception e) {

                Error error = Error.builder()
                        .status("500")
                        .path("/api/hash")
                        .timestamp(timeStamp)
                        .message(e.getMessage())
                        .build();

                return ResponseEntity.internalServerError().body(
                        UpFileResponse.builder()
                                .error(error)
                                .build()
                );
            }
        } else {
            Error error = Error.builder()
                    .status("400")
                    .path("/api/hash")
                    .timestamp(timeStamp)
                    .message("El parámetro ‘hash’ solo puede ser ‘SHA-256’ o ‘SHA-512’")
                    .build();
            return ResponseEntity.badRequest().body(
                    UpFileResponse.builder()
                            .error(error)
                            .build()
            );
        }
    }
}
