package com.github.psinalberth.common.application;

import com.github.psinalberth.common.domain.DomainEvent;

public interface Events {
    void publish(DomainEvent event);

    default void publish(Iterable<DomainEvent> events) {
        events.forEach(this::publish);
    }
}
