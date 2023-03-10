package com.uj15.timedeal.auth.service;

import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.auth.controller.dto.UserLoginRequest;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserPrincipal login(UserLoginRequest request) {
        Assert.notNull(request, "UserLoginRequest is null");

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("not found user name"));

        user.authenticate(request.getPassword());

        return UserPrincipal.from(user);
    }
}
