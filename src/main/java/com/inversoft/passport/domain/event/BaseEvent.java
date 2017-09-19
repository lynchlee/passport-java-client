/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

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
    @Type(value = JWTRefreshTokenRevokeEvent.class, name = "jwt.refresh-token.revoke"),
    @Type(value = JWTPublicKeyUpdateEvent.class, name = "jwt.public-key.update"),
    @Type(value = TestEvent.class, name = "test")
})
public abstract class BaseEvent {
  public ZonedDateTime createInstant;

  public UUID id;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseEvent baseEvent = (BaseEvent) o;
    return Objects.equals(createInstant, baseEvent.createInstant) &&
        Objects.equals(id, baseEvent.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createInstant, id);
  }

  /**
   * @return The type of this event.
   */
  public abstract EventType type();
}
