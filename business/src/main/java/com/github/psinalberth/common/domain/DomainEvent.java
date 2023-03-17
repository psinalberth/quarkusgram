package com.github.psinalberth.common.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {

    default UUID getId() {
        return UUID.randomUUID();
    }

    default LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }
}
