package com.example.api.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class Standard implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    
}
