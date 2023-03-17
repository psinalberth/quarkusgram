package com.github.psinalberth.user.application.port.out;

import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface UpdateUserPort {

    Uni<User> update(User user);
}
