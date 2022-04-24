package com.mpb.basic.auth.backend.service;

import com.mpb.basic.auth.backend.entity.auth.User;
import com.mpb.basic.auth.backend.web.req.LoginRequest;
import com.mpb.basic.auth.backend.web.res.JwtResponse;

import javax.validation.constraints.Email;
import java.util.List;

public interface UserService {
    User getByEmail(String email);
    Boolean isUserNameExist(String userName);
    Boolean isActivated(@Email String email);
    Boolean activate(@Email String email);

    JwtResponse login(LoginRequest loginRequest);

    User addUser(User user);
    List<User> all();
}
