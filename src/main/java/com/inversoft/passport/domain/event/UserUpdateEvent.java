/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.util.Objects;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;
import com.inversoft.passport.domain.Buildable;
import com.inversoft.passport.domain.User;

/**
 * Models the User Update Event (and can be converted to JSON).
 *
 * @author Brian Pontarelli
 */
public class UserUpdateEvent extends BaseEvent implements Buildable<UserUpdateEvent> {
  public User original;

  public User user;

  @JacksonConstructor
  public UserUpdateEvent() {
  }

  public UserUpdateEvent(User original, User user) {
    this.original = original;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserUpdateEvent that = (UserUpdateEvent) o;
    return Objects.equals(user, that.user) && Objects.equals(original, that.original);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, original);
  }

  public String toString() {
    return ToString.toString(this);
  }

  @Override
  public EventType type() {
    return EventType.UserUpdate;
  }
}
