package com.github.psinalberth.user.application.port.in;

import com.github.psinalberth.user.application.service.friendship.BeFriendsResult;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface BeFriendsWithUseCase {

    Uni<BeFriendsResult> beFriendsWith(BeFriends beFriends);
}
