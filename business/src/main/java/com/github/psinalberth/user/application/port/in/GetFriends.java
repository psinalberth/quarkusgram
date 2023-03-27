package com.github.psinalberth.user.application.port.in;

import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
public class GetFriends {

    @NotEmpty(message = "User id is required.")
    String userId;
}
