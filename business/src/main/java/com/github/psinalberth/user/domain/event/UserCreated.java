package com.github.psinalberth.user.domain.event;

import com.github.psinalberth.common.domain.DomainEvent;
import lombok.Value;

@Value(staticConstructor = "of")
public class UserCreated implements DomainEvent {

    String userId;
}
