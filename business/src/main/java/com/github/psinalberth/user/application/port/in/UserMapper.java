package com.github.psinalberth.user.application.port.in;

import com.github.psinalberth.user.domain.model.User;

public interface UserMapper {

    User toDomain(NewUser newUser);
}
