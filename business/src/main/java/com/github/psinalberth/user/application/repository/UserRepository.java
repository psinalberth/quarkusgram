package com.github.psinalberth.user.application.repository;

import com.github.psinalberth.user.application.port.out.AddFriendGateway;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.port.out.RequestFriendshipGateway;
import com.github.psinalberth.user.application.port.out.SaveUserPort;
import com.github.psinalberth.user.application.service.friendship.LoadFriendsPort;
import com.github.psinalberth.user.domain.model.Friend;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepository implements SaveUserPort, LoadUserPort, AddFriendGateway, RequestFriendshipGateway, LoadFriendsPort {

    private final UserDatabaseRepository delegate;

    @Override
    public Uni<User> save(User user) {
        return delegate.save(user);
    }

    @Override
    public Uni<User> loadById(String id) {
        return delegate.loadById(id);
    }

    @Override
    public Uni<User> loadByUsername(String username) {
        return delegate.loadByUsername(username);
    }

    @Override
    public Uni<User> addFriend(User user, User friend) {
        return delegate.addFriend(user, friend);
    }

    @Override
    public Uni<User> requestFriendship(User user, User friend) {
        return delegate.requestFriendship(user, friend);
    }

    @Override
    public Multi<Friend> loadFriends(User user) {
        return delegate.loadFriends(user);
    }
}

