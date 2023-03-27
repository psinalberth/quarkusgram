package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.out.AddFriendGateway;
import com.github.psinalberth.user.domain.enums.FriendshipStatus;
import com.github.psinalberth.user.domain.event.UserBecameFriends;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class PublicAccountFriendshipRule implements FriendshipRule {

    private final AddFriendGateway addFriendGateway;

    @Override
    public boolean canMake(Friendship friendship) {
        var friend = friendship.getFriend();
        return friend.hasPublicProfile();
    }

    @Override
    public Uni<BeFriendsResult> make(Friendship friendship) {
        return addFriendGateway.addFriend(friendship.getMe(), friendship.getFriend())
                .map(u -> Set.of(UserBecameFriends.of(friendship.getMe(), friendship.getFriend())))
                .map(events -> new BeFriendsResult("", "", FriendshipStatus.ACCEPTED, events));
    }
}
