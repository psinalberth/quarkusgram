package com.github.psinalberth.user.application.port.in;

import com.github.psinalberth.user.domain.model.Friend;
import io.smallrye.mutiny.Multi;

public interface GetFriendsUseCase {

    Multi<Friend> getFriends(GetFriends getFriends);
}
