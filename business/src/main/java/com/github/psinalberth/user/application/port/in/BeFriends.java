package com.github.psinalberth.user.application.port.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BeFriends {

    private String userId;

    @NotEmpty(message = "Friend id is required.")
    private String friendId;
}
