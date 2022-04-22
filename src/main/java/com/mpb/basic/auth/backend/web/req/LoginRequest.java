package com.mpb.basic.auth.backend.web.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @Email
    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
