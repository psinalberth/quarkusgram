package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.out.RequestFriendshipGateway;
import com.github.psinalberth.user.domain.enums.FriendshipStatus;
import com.github.psinalberth.user.domain.event.FriendshipRequested;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class PrivateAccountFriendshipRule implements FriendshipRule {

    private final RequestFriendshipGateway requestFriendshipGateway;

    @Override
    public boolean canMake(Friendship friendship) {
        var friend = friendship.getFriend();
        return friend.hasPrivateProfile();
    }

    @Override
    public Uni<BeFriendsResult> make(Friendship friendship) {
        return requestFriendshipGateway.requestFriendship(friendship.getMe(), friendship.getFriend())
                .map(u -> Set.of(FriendshipRequested.of(friendship.getMe(), friendship.getFriend())))
                .map(events -> new BeFriendsResult("", "", FriendshipStatus.REQUESTED, events));
    }
}
