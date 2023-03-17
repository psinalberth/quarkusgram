package com.github.psinalberth.user.application.service;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.user.application.port.in.NewUser;
import com.github.psinalberth.user.application.port.in.UserMapper;
import com.github.psinalberth.user.application.port.in.UserMapperImpl;
import com.github.psinalberth.user.application.port.out.PasswordEncryptorGateway;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.port.out.SaveUserPort;
import com.github.psinalberth.user.domain.exception.UsernameAlreadyInUseException;
import com.github.psinalberth.user.domain.model.User;
import com.github.psinalberth.user.fixtures.UserFixtures;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private SaveUserPort saveUserPort;

    @Mock
    private PasswordEncryptorGateway passwordEncryptorGateway;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Mock
    private Events events;

    @InjectMocks
    private RegisterUserService registerUserUseCase;

    @Nested
    class SuccessCases {

        @Test
        void shouldRegisterUserSuccessfully() {

            var newUser = UserFixtures.newUser();
            var user = UserFixtures.user();
            var userCaptor = ArgumentCaptor.forClass(User.class);

            when(loadUserPort.loadByUsername(newUser.getUsername()))
                    .thenReturn(Uni.createFrom().nullItem());

            when(passwordEncryptorGateway.encrypt(user.getPassword()))
                    .thenReturn(user.getPassword());

            when(saveUserPort.save(userCaptor.capture()))
                    .thenReturn(Uni.createFrom().item(user));

            var sub = registerUserUseCase.register(newUser)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertItem(user)
                    .assertCompleted();

            assertNotNull(user.getId());

            verify(userMapper, times(1))
                    .toDomain(newUser);

            verify(saveUserPort, times(1))
                    .save(any());

            verify(events, times(1))
                    .publish(any());
        }
    }

    @Nested
    class ErrorCases {

        @Test
        void shouldNotRegisterUser_whenUsernameAlreadyExists() {

            var uniUser = Uni.createFrom().item(new User());

            when(loadUserPort.loadByUsername(any()))
                    .thenReturn(uniUser);

            var sub = registerUserUseCase.register(new NewUser())
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertFailedWith(UsernameAlreadyInUseException.class)
                    .assertTerminated();

            verify(userMapper, never())
                    .toDomain(any(NewUser.class));

            verify(saveUserPort, never())
                    .save(any());
        }

        @Test
        void shouldNotRegisterUser_WhenUserValidationFails() {

            var newUser = new NewUser();

            when(loadUserPort.loadByUsername(any()))
                    .thenReturn(Uni.createFrom().nullItem());

            var sub = registerUserUseCase.register(newUser)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertFailedWith(ConstraintViolationException.class)
                    .assertTerminated();

            verify(userMapper, times(1))
                    .toDomain(newUser);

            verify(saveUserPort, never())
                    .save(any());
        }
    }
}