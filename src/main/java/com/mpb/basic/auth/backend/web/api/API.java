package com.mpb.basic.auth.backend.web.api;

import com.mpb.basic.auth.backend.web.res.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface API {
    static ResponseEntity<ApiResponse> send(Object o, HttpStatus status){
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(true)
                .body(o)
                .message("Done")
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(apiResponse, status);
    }
}
