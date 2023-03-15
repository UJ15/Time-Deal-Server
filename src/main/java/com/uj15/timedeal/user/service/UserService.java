package com.uj15.timedeal.user.service;

import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.user.controller.dto.UserCreateRequest;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(UserCreateRequest request) {
        Assert.notNull(request, "UserCreateRequest is null.");
        checkDuplicate(request.getUsername());

        User user = User.of(
                request.getUsername(),
                request.getPassword(),
                request.getRole()
        );

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(UserPrincipal userPrincipal) {
        Assert.notNull(userPrincipal, "userPrincipal is null.");

        return userRepository.findById(userPrincipal.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("not exist user"));
    }

    @Transactional
    public void deleteUser(UserPrincipal userPrincipal) {
        Assert.notNull(userPrincipal, "userPrincipal is null.");

        userRepository.deleteById(userPrincipal.getUserId());
    }

    private void checkDuplicate(String username) {
        userRepository.findByUsername(username)
                .ifPresent(u -> {
                    throw new IllegalArgumentException("username is already exist.");
                });
    }
}
