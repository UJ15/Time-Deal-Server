package com.uj15.timedeal.user.service;

import com.uj15.timedeal.user.controller.dto.UserCreateRequest;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.repository.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserCreateRequest request) {
        Assert.notNull(request, "UserCreateRequest is null.");

        User user = User.of(
                UUID.randomUUID(),
                request.getUsername(),
                request.getPassword(),
                request.getRole()
        );

        userRepository.save(user);
    }
}
