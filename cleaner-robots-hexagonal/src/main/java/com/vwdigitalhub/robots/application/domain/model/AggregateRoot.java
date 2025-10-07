package com.vwdigitalhub.robots.application.domain.model;


import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class AggregateRoot {
    private final UUID id;

    protected AggregateRoot() {
        this.id = UUID.randomUUID();
    }

    protected AggregateRoot(UUID id) {
        this.id = (id != null) ? id : UUID.randomUUID();
    }
}
