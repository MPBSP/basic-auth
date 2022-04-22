package com.mpb.basic.auth.backend.web.api;

import com.mpb.basic.auth.backend.entity.type.ERole;
import com.mpb.basic.auth.backend.web.exception.EntityNotFoundException;
import com.mpb.basic.auth.backend.web.req.LoginRequest;
import com.mpb.basic.auth.backend.web.req.RegisterRequest;
import com.mpb.basic.auth.backend.web.res.ApiResponse;
import com.mpb.basic.auth.backend.web.res.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.mpb.basic.auth.backend.BasicAuthBackendApplication.API_V;

@RequestMapping(path = "/"+API_V+"/auth")
public interface Auth {
    @PostMapping(path = "/login")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/role")
    ResponseEntity<ApiResponse> createRoles(@RequestBody List<ERole> roleList);

    @PostMapping("/sign-up")
    ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
            throws EntityNotFoundException;
}
