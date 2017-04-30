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
 * Models the Refresh Token Revoke Event (and can be converted to JSON).
 *
 * @author Brian Pontarelli
 */
public class UserRefreshTokenRevokeEvent extends BaseEvent implements Buildable<UserRefreshTokenRevokeEvent>, ApplicationEvent {
  public Map<UUID, Integer> applicationTimeToLiveInSeconds = new TreeMap<>();

  public UUID userId;

  @JacksonConstructor
  public UserRefreshTokenRevokeEvent() {
  }

  public UserRefreshTokenRevokeEvent(UUID userId, UUID applicationId, int timeToLiveInSeconds) {
    this.applicationTimeToLiveInSeconds.put(applicationId, timeToLiveInSeconds);
    this.userId = userId;
  }

  public UserRefreshTokenRevokeEvent(UUID userId, Map<UUID, Integer> applicationTimeToLiveInSeconds) {
    this.applicationTimeToLiveInSeconds.putAll(applicationTimeToLiveInSeconds);
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRefreshTokenRevokeEvent that = (UserRefreshTokenRevokeEvent) o;
    return Objects.equals(applicationTimeToLiveInSeconds, that.applicationTimeToLiveInSeconds) && Objects.equals(userId, that.userId);
  }

  @Override
  public List<UUID> applicationIds() {
    return new ArrayList<>(applicationTimeToLiveInSeconds.keySet());
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationTimeToLiveInSeconds, userId);
  }

  public String toString() {
    return ToString.toString(this);
  }

  @Override
  public EventType type() {
    return EventType.UserRefreshTokenRevoke;
  }
}
