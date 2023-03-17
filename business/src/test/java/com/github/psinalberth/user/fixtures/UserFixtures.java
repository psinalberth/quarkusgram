package com.github.psinalberth.user.fixtures;

import com.github.psinalberth.user.application.port.in.NewUser;
import com.github.psinalberth.user.domain.model.User;

import java.util.UUID;

public final class UserFixtures {

    private UserFixtures() {}

    public static NewUser newUser() {
        var newUser = new NewUser();

        newUser.setUsername("jdoe1990");
        newUser.setEmail("john.doe@domain.com");
        newUser.setPhoneNumber("5531912345678");
        newUser.setPassword("s3cr3t");

        return newUser;
    }

    public static User user() {
        var user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setUsername("jdoe1990");
        user.setEmail("john.doe@domain.com");
        user.setPhoneNumber("5531912345678");
        user.setPassword("s3cr3t");

        return user;
    }

    public static User privateUser() {
        var user = user();
        user.setPrivateProfile(true);
        return user;
    }
}
