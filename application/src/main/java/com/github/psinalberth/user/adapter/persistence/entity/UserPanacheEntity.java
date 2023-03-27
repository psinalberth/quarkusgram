package com.github.psinalberth.user.adapter.persistence.entity;

import com.github.psinalberth.user.domain.model.Friend;
import com.github.psinalberth.user.domain.model.User;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@MongoEntity(collection = "users")
@EqualsAndHashCode
public class UserPanacheEntity {

    public ObjectId id;
    public String fullName;
    public String username;
    public String email;
    public String phoneNumber;
    public String password;
    public boolean privateProfile;
    public Set<FriendPanacheEntity> friends;
    public Set<UserPanacheEntity> possibleFriends;
    public Set<UserPanacheEntity> blockedUsers;

    public UserPanacheEntity() {

    }

    public UserPanacheEntity(String id) {
        this.id = new ObjectId(id);
    }

    public UserPanacheEntity(User user) {
        this.fullName = user.getFullName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.password = user.getPassword();
        this.privateProfile = user.hasPrivateProfile();

        Optional.ofNullable(user.getId())
                .ifPresent(id -> this.id = new ObjectId(id));
    }

    public User toUser() {
        var user = new User();

        user.setId(this.id.toString());
        user.setFullName(this.fullName);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPhoneNumber(this.phoneNumber);
        user.setPrivateProfile(this.privateProfile);
        user.setPassword(this.password);

        if (this.friends != null) {
            user.setFriends(this.friends.stream().map(friend -> Friend.of(friend.id.toString(), "")).collect(Collectors.toSet()));
        }

        return user;
    }

    public boolean addFriend(FriendPanacheEntity friend) {
        if (this.friends == null)
            this.friends = new HashSet<>();

        return this.friends.add(friend);
    }

    public boolean removeFriend(FriendPanacheEntity friend) {
        if (this.friends == null)
            return false;

        return this.friends.remove(friend);
    }

    public boolean blockUser(UserPanacheEntity user) {
        if (this.blockedUsers == null)
            this.blockedUsers = new HashSet<>();

        return this.blockedUsers.add(user);
    }

    public boolean unblockUser(UserPanacheEntity user) {
        if (this.blockedUsers == null)
            return false;

        return this.blockedUsers.remove(user);
    }

    public boolean addPossibleFriend(UserPanacheEntity user) {
        if (this.possibleFriends == null)
            this.possibleFriends = new HashSet<>();

        return this.possibleFriends.add(user);
    }

    public boolean removePossibleFriend(UserPanacheEntity user) {
        if (this.possibleFriends == null)
            return false;

        return this.possibleFriends.remove(user);
    }
}
