package com.github.psinalberth.user.application.service;

import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.service.friendship.FriendshipRule;
import com.github.psinalberth.user.domain.exception.UserNotFoundException;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class FriendshipMaker {

    private final Set<FriendshipRule> friendshipRules;

    public Uni<User> make(Friendship friendship) {
        return validate(friendship)
                .chain(() -> friendshipRules.stream()
                        .filter(rule -> rule.canMake(friendship))
                        .findFirst()
                        .orElseThrow().make(friendship));
    }

    private Uni<Void> validate(Friendship friendship) {

        var me = friendship.getMe();
        var friend = friendship.getFriend();

        if (me == null) {
            return Uni.createFrom()
                    .failure(() -> new UserNotFoundException("We couldn't find you on database. Wait, what?"));
        }

        if (friend == null) {
            return Uni.createFrom()
                    .failure(() -> new UserNotFoundException("Friend not found with given id."));
        }

        return Uni.createFrom().voidItem();
    }
}
