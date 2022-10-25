package com.example.upfile.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {

    private String timestamp;
    private String status;
    private String message;
    private String path;
}
