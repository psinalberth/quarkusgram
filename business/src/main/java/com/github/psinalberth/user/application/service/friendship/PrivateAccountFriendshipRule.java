package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.out.RequestFriendshipGateway;
import com.github.psinalberth.user.domain.event.FriendshipRequested;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrivateAccountFriendshipRule implements FriendshipRule {

    private final RequestFriendshipGateway requestFriendshipGateway;
    private final Events events;

    @Override
    public boolean canMake(Friendship friendship) {
        var friend = friendship.getFriend();
        return friend.hasPrivateProfile();
    }

    @Override
    public Uni<User> make(Friendship friendship) {
        var result = requestFriendshipGateway.requestFriendship(friendship.getMe(), friendship.getFriend());
        events.publish(FriendshipRequested.of(friendship.getMe(), friendship.getFriend()));
        return result;
    }
}
