package com.example.upfile.model.http;

import com.example.upfile.model.Error;
import com.example.upfile.model.entity.File;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpFileResponse {
    private String algorithm;
    private List<File> documents;
    private Error error;
}
