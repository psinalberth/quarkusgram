package com.github.psinalberth.user.adapter.persistence.entity;

import io.quarkus.mongodb.panache.common.ProjectionFor;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

@ProjectionFor(UserPanacheEntity.class)
@EqualsAndHashCode
public class FriendPanacheEntity {

    public ObjectId id;

    public FriendPanacheEntity(String id) {
        this.id = new ObjectId(id);
    }
}
