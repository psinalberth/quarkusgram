package com.github.psinalberth.user.application.port.in;

import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface BeFriendsWithUseCase {

    Uni<User> beFriendsWith(BeFriends beFriends);
}
