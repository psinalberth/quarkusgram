package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface FriendshipRule {

    boolean canMake(Friendship friendship);

    Uni<User> make(Friendship friendship);
}
