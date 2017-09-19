/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;
import com.inversoft.passport.domain.Buildable;

/**
 * Models the JWT public key Refresh Token Revoke Event (and can be converted to JSON). This event might be for a single
 * token, a user or an entire application.
 *
 * @author Brian Pontarelli
 */
public class JWTPublicKeyUpdateEvent extends BaseEvent implements Buildable<JWTPublicKeyUpdateEvent>, ApplicationEvent {
  public Set<UUID> applicationIds;

  @JacksonConstructor
  public JWTPublicKeyUpdateEvent() {
  }

  public JWTPublicKeyUpdateEvent(UUID applicationId) {
    this.applicationIds = new HashSet<>();
    this.applicationIds.add(applicationId);
  }

  public JWTPublicKeyUpdateEvent(Set<UUID> applicationIds) {
    this.applicationIds = applicationIds;
  }

  @Override
  public Collection<UUID> applicationIds() {
    return applicationIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JWTPublicKeyUpdateEvent that = (JWTPublicKeyUpdateEvent) o;
    return super.equals(o) &&
        Objects.equals(applicationIds, that.applicationIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), applicationIds);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }

  @Override
  public EventType type() {
    return EventType.JWTPublicKeyUpdate;
  }
}
