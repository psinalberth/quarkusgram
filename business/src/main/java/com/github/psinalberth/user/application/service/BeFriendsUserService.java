package com.github.psinalberth.user.application.service;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.common.application.SaveToOutboxPort;
import com.github.psinalberth.common.domain.DomainEvent;
import com.github.psinalberth.common.domain.UseCase;
import com.github.psinalberth.user.application.port.in.BeFriends;
import com.github.psinalberth.user.application.port.in.BeFriendsWithUseCase;
import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.service.friendship.BeFriendsResult;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class BeFriendsUserService implements BeFriendsWithUseCase {

    private final LoadUserPort loadUserPort;
    private final FriendshipMaker friendshipMaker;
    private final Events events;

    private SaveToOutboxPort outboxPort;


    @Override
    public Uni<BeFriendsResult> beFriendsWith(BeFriends beFriends) {

        var currentUser = loadUserPort.loadById(beFriends.getUserId());
        var newFriend = loadUserPort.loadById(beFriends.getFriendId());

        return Uni.combine().all()
                .unis(currentUser, newFriend)
                .asTuple()
                .onItem().ifNotNull()
                .transform(tuple -> Friendship.of(tuple.getItem1(), tuple.getItem2()))
                .onItem()
                .transformToUni(friendshipMaker::make)
                .invoke(result -> events.publish((DomainEvent) result.getAssociatedEvents()));
    }
}
