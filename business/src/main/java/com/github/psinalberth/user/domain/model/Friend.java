package com.github.psinalberth.user.domain.model;

import com.github.psinalberth.common.domain.Domain;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value(staticConstructor = "of")
public class Friend implements Domain {

    @NotEmpty(message = "Friend id is required.")
    String id;


    String username;
}
