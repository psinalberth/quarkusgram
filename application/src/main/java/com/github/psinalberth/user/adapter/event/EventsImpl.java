package com.github.psinalberth.user.adapter.event;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.common.domain.DomainEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventsImpl implements Events {
    @Override
    public void publish(DomainEvent event) {
        System.out.println(event);
    }
}
