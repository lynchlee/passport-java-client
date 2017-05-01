/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base-class for all Passport events.
 *
 * @author Brian Pontarelli
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @Type(value = UserActionEvent.class, name = "user.action"),
    @Type(value = UserCreateEvent.class, name = "user.create"),
    @Type(value = UserUpdateEvent.class, name = "user.update"),
    @Type(value = UserDeleteEvent.class, name = "user.delete"),
    @Type(value = UserDeactivateEvent.class, name = "user.deactivate"),
    @Type(value = UserReactivateEvent.class, name = "user.reactivate"),
    @Type(value = UserBulkCreateEvent.class, name = "user.bulk.create"),
    @Type(value = JWTRefreshTokenRevokeEvent.class, name = "jwt.refresh-token.revoke")
})
public abstract class BaseEvent {
  /**
   * @return The type of this event.
   */
  public abstract EventType type();
}
