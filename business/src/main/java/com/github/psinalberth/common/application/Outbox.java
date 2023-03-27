package com.github.psinalberth.common.application;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class Outbox {

    private UUID id;
    private String event;
    private LocalDateTime createdAt;
    private Map<String, Object> payload;
}
