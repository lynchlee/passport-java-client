/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
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
