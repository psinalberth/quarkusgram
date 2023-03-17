package com.github.psinalberth.user.application.repository;

import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface UserDatabaseRepository {
    Uni<User> save(User user);

    Uni<User> loadById(String id);

    Uni<User> loadByUsername(String username);

    Uni<User> addFriend(User user, User friend);

    Uni<User> requestFriendship(User user, User friend);
}
