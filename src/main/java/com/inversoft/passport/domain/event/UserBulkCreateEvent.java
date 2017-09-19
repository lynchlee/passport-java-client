/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.util.List;
import java.util.Objects;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;
import com.inversoft.passport.domain.Buildable;
import com.inversoft.passport.domain.User;

/**
 * Models the User Bulk Create Event (and can be converted to JSON).
 *
 * @author Brian Pontarelli
 */
public class UserBulkCreateEvent extends BaseEvent implements Buildable<UserBulkCreateEvent> {
  public List<User> users;

  @JacksonConstructor
  public UserBulkCreateEvent() {
  }

  public UserBulkCreateEvent(List<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserBulkCreateEvent that = (UserBulkCreateEvent) o;
    return super.equals(o) &&
        Objects.equals(users, that.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), users);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }

  @Override
  public EventType type() {
    return EventType.UserBulkCreate;
  }
}
