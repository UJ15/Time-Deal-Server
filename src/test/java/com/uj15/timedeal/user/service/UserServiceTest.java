package com.uj15.timedeal.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.controller.dto.UserCreateRequest;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Nested
    @DisplayName("createUser 메서드 테스트")
    class Describe {

        @ParameterizedTest
        @NullSource
        @DisplayName("인자가 null일경우 IllegalArgumentException을 반환한다")
        void itThrowIllegalArgumentException(UserCreateRequest requestDto) {
            //then
            Assertions.assertThatThrownBy(() -> userService.createUser(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("정상적인 인자를 받을 경우 repository save메서드를 호출한다")
        void itCallRepositorySave() {
            //given
            UserCreateRequest requestDto = new UserCreateRequest("username", "password", Role.USER);

            //when
            userService.createUser(requestDto);

            //then
            verify(userRepository).save(any(User.class));
        }
    }
}
