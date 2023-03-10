package com.uj15.timedeal.auth.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.uj15.timedeal.auth.controller.dto.UserLoginRequest;
import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
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
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthService authService;

    @Nested
    @DisplayName("login 메서드 테스트")
    class DescribeLogin {

        @ParameterizedTest
        @NullSource
        @DisplayName("인자가 null일경우 IllegalArgumentException을 반환한다")
        void itThrowIllegalArgumentException(UserLoginRequest requestDto) {
            //then
            Assertions.assertThatThrownBy(() -> authService.login(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("존재하지 않는 유저 아이디를 인자로 받을경우 IllegalArgumentException 반환")
        void itInvalidUsername() {
            //given
            UserLoginRequest requestDto = new UserLoginRequest("invalid", "password");
            when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

            //then
            Assertions.assertThatThrownBy(() -> authService.login(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("비밀번호가 다를경우 IllegalArgumentException 반환")
        void itInvalidPassword() {
            //given
            UserLoginRequest requestDto = new UserLoginRequest("username", "invalid");
            User user = new User(UUID.randomUUID(), "username", "password", Role.USER);
            when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

            //then
            Assertions.assertThatThrownBy(() -> authService.login(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("정상적인 인자를 받을 경우 repository save메서드를 호출한다")
        void itCallRepositorySave() {
            //given
            UserLoginRequest requestDto = new UserLoginRequest("username", "password");
            User user = new User(UUID.randomUUID(), "username", "password", Role.USER);
            when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

            //when
            authService.login(requestDto);

            //then
            verify(userRepository).findByUsername(any());
        }
    }

}