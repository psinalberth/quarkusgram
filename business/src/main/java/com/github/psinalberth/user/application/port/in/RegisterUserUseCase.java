package com.github.psinalberth.user.application.port.in;

import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface RegisterUserUseCase {

    Uni<User> register(NewUser newUser);
}
