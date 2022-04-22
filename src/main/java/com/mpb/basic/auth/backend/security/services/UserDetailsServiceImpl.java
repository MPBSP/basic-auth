package com.mpb.basic.auth.backend.security.services;

import com.mpb.basic.auth.backend.entity.auth.User;
import com.mpb.basic.auth.backend.repository.UserRepository;
import com.mpb.basic.auth.backend.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository
                .findByUserName(email)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "User Not Found with username = ", email));
        return UserDetailsImpl.build(user);
    }
}
