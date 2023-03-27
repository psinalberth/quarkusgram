package com.github.psinalberth.user.application.service;

import com.github.psinalberth.common.domain.UseCase;
import com.github.psinalberth.user.application.port.in.GetFriends;
import com.github.psinalberth.user.application.port.in.GetFriendsUseCase;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.service.friendship.LoadFriendsPort;
import com.github.psinalberth.user.domain.exception.UserNotFoundException;
import com.github.psinalberth.user.domain.model.Friend;
import io.smallrye.mutiny.Multi;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetFriendsUserService implements GetFriendsUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadFriendsPort loadFriendsPort;

    @Override
    public Multi<Friend> getFriends(GetFriends getFriends) {
        return loadUserPort.loadById(getFriends.getUserId())
                .onItem().ifNull().failWith(() -> new UserNotFoundException("We could not find user with given id."))
                .onItem().transformToMulti(loadFriendsPort::loadFriends);
    }
}
