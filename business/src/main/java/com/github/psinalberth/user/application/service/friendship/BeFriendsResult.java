package com.github.psinalberth.user.application.service.friendship;

import com.github.psinalberth.common.domain.DomainEvent;
import com.github.psinalberth.user.domain.enums.FriendshipStatus;
import lombok.Value;

import java.util.Set;

@Value
public class BeFriendsResult {

    String userId;
    String friendId;
    FriendshipStatus status;
    Set<? extends DomainEvent> associatedEvents;
}
