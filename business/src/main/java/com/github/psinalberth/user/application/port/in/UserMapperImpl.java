package com.github.psinalberth.user.application.port.in;

import com.github.psinalberth.user.domain.model.User;

public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(NewUser newUser) {

        var user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setPassword(newUser.getPassword());

        return user;
    }
}
