package com.github.psinalberth.common.application;

import io.smallrye.mutiny.Uni;

public interface SaveToOutboxPort {

    Uni<Outbox> save(Outbox outbox);
}
