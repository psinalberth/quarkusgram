package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.user.application.port.in.Friendship;
import io.smallrye.mutiny.Uni;

public interface FriendshipRule {

    boolean canMake(Friendship friendship);

    Uni<BeFriendsResult> make(Friendship friendship);
}
