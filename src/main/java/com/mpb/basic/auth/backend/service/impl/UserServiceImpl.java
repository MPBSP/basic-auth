package com.mpb.basic.auth.backend.service.impl;

import com.mpb.basic.auth.backend.entity.auth.User;
import com.mpb.basic.auth.backend.entity.auth.UserStatus;
import com.mpb.basic.auth.backend.repository.RoleRepository;
import com.mpb.basic.auth.backend.repository.UserRepository;
import com.mpb.basic.auth.backend.security.jwt.JwtUtils;
import com.mpb.basic.auth.backend.security.services.UserDetailsImpl;
import com.mpb.basic.auth.backend.service.UserService;
import com.mpb.basic.auth.backend.web.exception.EmailAlreadyExistException;
import com.mpb.basic.auth.backend.web.exception.EntityNotFoundException;
import com.mpb.basic.auth.backend.web.exception.ExceptionWithMessage;
import com.mpb.basic.auth.backend.web.req.LoginRequest;
import com.mpb.basic.auth.backend.web.res.JwtResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder encoder,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> byUserName = userRepository.findByUserName(email);
        if (byUserName.isEmpty()) {
            throw new EntityNotFoundException(User.class, "email", email);
        }
        return byUserName.get();
    }

    @Override
    public Boolean isUserNameExist(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public Boolean isActivated(String email) {
        return this.getByEmail(email).getUserStatus().equals(UserStatus.ACTIVATED);
    }

    @Override
    public Boolean activate(String email) {
        return Boolean.FALSE; // TODO
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        if (!this.isActivated(loginRequest.getUserName())){
            throw new ExceptionWithMessage("This account is deactivated");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return JwtResponse
                .builder()
                .id(userDetails.getId())
                .roles(roles)
                .token(jwt)
                .username(userDetails.getUsername())
                .build();
    }

    @Override
    public User addUser(User user) {
        if (this.isUserNameExist(user.getUserName())) {
            throw new EmailAlreadyExistException();
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
