package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.out.AddFriendGateway;
import com.github.psinalberth.user.domain.event.UserBecameFriends;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PublicAccountFriendshipRule implements FriendshipRule {

    private final AddFriendGateway addFriendGateway;
    private final Events events;

    @Override
    public boolean canMake(Friendship friendship) {
        var friend = friendship.getFriend();
        return friend.hasPublicProfile();
    }

    @Override
    public Uni<User> make(Friendship friendship) {
        var result = addFriendGateway.addFriend(friendship.getMe(), friendship.getFriend());
        events.publish(UserBecameFriends.of(friendship.getMe(), friendship.getFriend()));
        return result;
    }
}
