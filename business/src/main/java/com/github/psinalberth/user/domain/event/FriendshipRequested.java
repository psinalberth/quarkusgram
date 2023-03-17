package com.github.psinalberth.user.domain.event;

import com.github.psinalberth.common.domain.DomainEvent;
import com.github.psinalberth.user.domain.model.User;
import lombok.Value;

@Value(staticConstructor = "of")
public class FriendshipRequested implements DomainEvent {

    User me;
    User friend;
}
