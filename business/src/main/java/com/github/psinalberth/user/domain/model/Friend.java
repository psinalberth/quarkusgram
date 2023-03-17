package com.github.psinalberth.user.domain.model;

import com.github.psinalberth.common.domain.Domain;
import lombok.Value;

@Value(staticConstructor = "of")
public class Friend implements Domain {

    String id;
    String username;
}
