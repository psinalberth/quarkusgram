package com.github.psinalberth.user.domain.model;

import com.github.psinalberth.common.domain.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class User implements Domain {

    private String id;

    @NotEmpty(message = "Username is required.")
    private String username;

    private String fullName;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotEmpty(message = "Phone number is required.")
    private String phoneNumber;

    @NotEmpty(message = "Password is required.")
    private String password;

    private Set<Friend> friends;

    private boolean privateProfile;

    public boolean addFriend(Friend friend) {
        if (this.friends == null)
            this.friends = new HashSet<>();
        return this.friends.add(friend);
    }

    public boolean removeFriend(Friend friend) {
        if (this.friends == null)
            return false;
        return this.friends.remove(friend);
    }

    public boolean hasPrivateProfile() {
        return privateProfile;
    }

    public boolean hasPublicProfile() {
        return !hasPrivateProfile();
    }
}
