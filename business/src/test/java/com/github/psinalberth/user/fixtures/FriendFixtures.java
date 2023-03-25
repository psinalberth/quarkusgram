package com.github.psinalberth.user.fixtures;

import com.github.psinalberth.user.domain.model.Friend;

import java.util.UUID;

public final class FriendFixtures {

    public static Friend someFriend() {
        return Friend.of(UUID.randomUUID().toString(), "John Smith");
    }
}
