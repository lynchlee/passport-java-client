/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;
import com.inversoft.passport.domain.Buildable;

/**
 * Models the Refresh Token Revoke Event (and can be converted to JSON). This event might be for a single token, a user
 * or an entire application.
 *
 * @author Brian Pontarelli
 */
public class JWTRefreshTokenRevokeEvent extends BaseEvent implements Buildable<JWTRefreshTokenRevokeEvent>, ApplicationEvent {
  public UUID applicationId;

  public Map<UUID, Integer> applicationTimeToLiveInSeconds = new TreeMap<>();

  public UUID userId;

  @JacksonConstructor
  public JWTRefreshTokenRevokeEvent() {
  }

  public JWTRefreshTokenRevokeEvent(UUID userId, UUID applicationId, int timeToLiveInSeconds) {
    this.applicationId = applicationId;
    this.applicationTimeToLiveInSeconds.put(applicationId, timeToLiveInSeconds);
    this.userId = userId;
  }

  public JWTRefreshTokenRevokeEvent(UUID userId, Map<UUID, Integer> applicationTimeToLiveInSeconds) {
    this.applicationTimeToLiveInSeconds.putAll(applicationTimeToLiveInSeconds);
    this.userId = userId;
  }

  @Override
  public List<UUID> applicationIds() {
    return new ArrayList<>(applicationTimeToLiveInSeconds.keySet());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JWTRefreshTokenRevokeEvent that = (JWTRefreshTokenRevokeEvent) o;
    return super.equals(o) &&
        Objects.equals(applicationTimeToLiveInSeconds, that.applicationTimeToLiveInSeconds) && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), applicationTimeToLiveInSeconds, userId);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }

  @Override
  public EventType type() {
    return EventType.JWTRefreshTokenRevoke;
  }
}
