package com.mpb.basic.auth.backend.web.req;

import com.mpb.basic.auth.backend.entity.type.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    private String email;

    private String firstName;

    private String lastName;

    @NotBlank
    private String password;

    private List<ERole> roleList;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
