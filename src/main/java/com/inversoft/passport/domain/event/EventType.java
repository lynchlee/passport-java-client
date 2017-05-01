/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Models the event types that Passport produces.
 *
 * @author Brian Pontarelli
 */
public enum EventType {
  UserDelete("user.delete"),

  UserCreate("user.create"),

  UserUpdate("user.update"),

  UserDeactivate("user.deactivate"),

  UserBulkCreate("user.bulk.create"),

  UserReactivate("user.reactivate"),

  UserAction("user.action"),

  JWTRefreshTokenRevoke("jwt.refresh-token.revoke"),

  JWTPublicKeyUpdate("jwt.public-key.update");

  private static Map<String, EventType> nameMap = new HashMap<>(EventType.values().length);

  private final String eventName;

  EventType(String eventName) {
    this.eventName = eventName;
  }

  @JsonCreator
  public static EventType forValue(String value) {
    return nameMap.get(value);
  }

  @JsonValue
  public String eventName() {
    return eventName;
  }

  static {
    for (EventType eventType : EventType.values()) {
      nameMap.put(eventType.eventName(), eventType);
    }
  }
}
