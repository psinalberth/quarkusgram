package com.github.psinalberth.user.application.service;

import com.github.psinalberth.common.domain.Domain;
import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.common.domain.UseCase;
import com.github.psinalberth.user.application.port.in.NewUser;
import com.github.psinalberth.user.application.port.in.RegisterUserUseCase;
import com.github.psinalberth.user.application.port.in.UserMapper;
import com.github.psinalberth.user.application.port.out.PasswordEncryptorGateway;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.port.out.SaveUserPort;
import com.github.psinalberth.user.domain.event.UserCreated;
import com.github.psinalberth.user.domain.exception.UsernameAlreadyInUseException;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final UserMapper userMapper;
    private final PasswordEncryptorGateway passwordEncryptorGateway;
    private final Events events;

    @Override
    public Uni<User> register(NewUser newUser) {
        return loadUserPort.loadByUsername(newUser.getUsername())
                .onItem()
                    .ifNotNull()
                    .failWith(() -> new UsernameAlreadyInUseException("Username is already in use."))
                .onItem()
                    .ifNull()
                    .continueWith(() -> userMapper.toDomain(newUser))
                .invoke(Domain::validate)
                .invoke(user -> user.setPassword(passwordEncryptorGateway.encrypt(user.getPassword())))
                .flatMap(saveUserPort::save)
                .invoke(registeredUser -> events.publish(UserCreated.of(registeredUser.getId())));
    }
}
