package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.user.domain.model.Friend;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Multi;

public interface LoadFriendsPort {

    Multi<Friend> loadFriends(User user);
}
