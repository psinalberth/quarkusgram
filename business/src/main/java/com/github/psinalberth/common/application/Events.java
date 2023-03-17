package com.github.psinalberth.common.application;

import com.github.psinalberth.common.domain.DomainEvent;

public interface Events {
    void publish(DomainEvent event);
}
