package com.uj15.timedeal.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.controller.dto.UserCreateRequest;
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

    @Nested
    @DisplayName("getUser 메서드 테스트")
    class DescribeGetUser {

        @NullSource
        @ParameterizedTest
        @DisplayName("인자가 null일경우 IllegalArgumentException을 반환한다.")
        void itThrowIllegalArgumentExceptionByNullArgument(UserPrincipal principal) {
            //then
            Assertions.assertThatThrownBy(() -> userService.getUser(principal))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("존재하지 않는 유저일경우 IllegalArgumentException을 반환")
        void itThrowIllegalArgumentExceptionByNotExistUser() {
            //given
            User user = User.of("username", "password", Role.USER);
            UserPrincipal principal = UserPrincipal.from(user);
            when(userRepository.findById(any())).thenReturn(Optional.empty());

            //then
            Assertions.assertThatThrownBy(() -> userService.getUser(principal))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("정상적인 인자를 받을 경우 해당 ID의 유저 반환")
        void itReturnUserById() {
            //given
            User expected = User.of("username", "password", Role.USER);
            UserPrincipal principal = UserPrincipal.from(expected);

            when(userRepository.findById(any())).thenReturn(Optional.of(expected));

            //when
            User actual = userService.getUser(principal);

            //then
            Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드 테서트")
    class DescribeDeleteUserTest {

        @NullSource
        @ParameterizedTest
        @DisplayName("인자가 null일경우 IllegalArgumentException을 반환한다.")
        void itThrowIllegalArgumentExceptionByNullArgument(UserPrincipal principal) {
            //then
            Assertions.assertThatThrownBy(() -> userService.deleteUser(principal))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("해당 세션의 유저를 삭제")
        void it() {
            //given
            User expected = User.of("username", "password", Role.USER);
            UserPrincipal principal = UserPrincipal.from(expected);

            //when
            userService.deleteUser(principal);

            //then
            verify(userRepository).deleteById(principal.getUserId());
        }
    }
}
