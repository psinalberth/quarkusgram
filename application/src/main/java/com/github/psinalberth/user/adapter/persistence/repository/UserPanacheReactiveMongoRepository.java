package com.github.psinalberth.user.adapter.persistence.repository;

import com.github.psinalberth.user.adapter.persistence.entity.UserPanacheEntity;
import com.github.psinalberth.user.application.repository.UserDatabaseRepository;
import com.github.psinalberth.user.domain.model.Friend;
import com.github.psinalberth.user.domain.model.User;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniOnNotNull;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;

@ApplicationScoped
public class UserPanacheReactiveMongoRepository implements UserDatabaseRepository,
        ReactivePanacheMongoRepository<UserPanacheEntity> {

    @Override
    public Uni<User> save(User user) {
        return persistOrUpdate(new UserPanacheEntity(user))
                .map(UserPanacheEntity::toUser);
    }

    @Override
    public Uni<User> loadById(String id) {
        return findById(new ObjectId(id))
                .onItem().ifNotNull()
                .transform(UserPanacheEntity::toUser);
    }

    @Override
    public Uni<User> loadByUsername(String username) {
        return findByUsername(username)
                .onItem().ifNotNull()
                .transform(UserPanacheEntity::toUser);
    }

    @Override
    public Uni<User> addFriend(User user, User friend) {
        return retrieveUser(user)
                .transform(me -> {
                    me.addFriend(new UserPanacheEntity(friend.getId()));
                    return me;
                })
                .call(this::update)
                .map(UserPanacheEntity::toUser);
    }

    @Override
    public Uni<User> requestFriendship(User user, User friend) {
        return retrieveUser(user)
                .transform(me -> {
                    me.addPossibleFriend(new UserPanacheEntity(friend.getId()));
                    return me;
                })
                .call(this::update)
                .map(UserPanacheEntity::toUser);
    }

    @Override
    public Multi<Friend> loadFriends(User user) {
        return retrieveUser(user)
                .transform(me -> me.friends)
                .replaceIfNullWith(Collections.emptySet())
                .toMulti()
                .flatMap(userPanacheEntities -> Multi.createFrom().iterable(userPanacheEntities))
                .flatMap(u -> loadById(u.id.toString()).toMulti())
                .map(u -> Friend.of(u.getId(), u.getUsername()));

    }

    private UniOnNotNull<UserPanacheEntity> retrieveUser(User user) {
        return findById(new ObjectId(user.getId()))
                .onItem()
                .ifNotNull();
    }
    public Uni<UserPanacheEntity> findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
