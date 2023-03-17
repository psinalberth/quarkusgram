package com.github.psinalberth.user.application.port.in;

import com.github.psinalberth.user.domain.model.User;
import lombok.Value;

@Value(staticConstructor = "of")
public class Friendship {

    User me;
    User friend;
}
