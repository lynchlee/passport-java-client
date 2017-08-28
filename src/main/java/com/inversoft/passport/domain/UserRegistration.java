/*
 * Copyright (c) 2015, Inversoft Inc., All Rights Reserved
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
package com.inversoft.passport.domain;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inversoft.json.ToString;
import static com.inversoft.passport.domain.util.Normalizer.trim;
import static com.inversoft.passport.domain.util.Normalizer.trimToNull;

/**
 * User registration information for a single application.
 *
 * @author Brian Pontarelli
 */
public class UserRegistration implements Buildable<UserRegistration> {
  @JsonIgnore
  public Application application;

  public UUID applicationId;

  public String authenticationToken;

  public UUID cleanSpeakId;

  public UserData data;

  public UUID id;

  public ZonedDateTime insertInstant;

  public ZonedDateTime lastLoginInstant;

  public SortedSet<String> roles = new TreeSet<>();

  @JsonIgnore
  public UUID userId;

  public String username;

  public ContentStatus usernameStatus;

  public UserRegistration() {
  }

  public UserRegistration(UUID id, UUID applicationId, UUID userId, ZonedDateTime lastLoginInstant, String username,
                          ContentStatus usernameStatus, UUID cleanSpeakId, UserData data, String... roles) {
    this.id = id;
    this.applicationId = applicationId;
    this.userId = userId;
    this.cleanSpeakId = cleanSpeakId;
    this.data = data;
    this.lastLoginInstant = lastLoginInstant;
    this.username = username;
    this.usernameStatus = usernameStatus;
    Collections.addAll(this.roles, roles);
  }

  public UserRegistration(UserRegistration userRegistration) {
    this.application = userRegistration.application;
    this.applicationId = userRegistration.applicationId;
    this.cleanSpeakId = userRegistration.cleanSpeakId;
    this.id = userRegistration.id;
    this.insertInstant = userRegistration.insertInstant;
    this.lastLoginInstant = userRegistration.lastLoginInstant;
    this.roles.addAll(userRegistration.roles);
    this.userId = userRegistration.userId;
    this.username = userRegistration.username;
    this.usernameStatus = userRegistration.usernameStatus;

    if (userRegistration.data != null) {
      this.data = new UserData(userRegistration.data);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserRegistration)) {
      return false;
    }
    UserRegistration that = (UserRegistration) o;
    return Objects.equals(applicationId, that.applicationId) &&
        Objects.equals(cleanSpeakId, that.cleanSpeakId) &&
        Objects.equals(data, that.data) &&
        Objects.equals(insertInstant, that.insertInstant) &&
        Objects.equals(lastLoginInstant, that.lastLoginInstant) &&
        Objects.equals(roles, that.roles) &&
        Objects.equals(authenticationToken, that.authenticationToken) &&
        Objects.equals(userId, that.userId) &&
        Objects.equals(username, that.username) &&
        Objects.equals(usernameStatus, that.usernameStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationId, cleanSpeakId, data, insertInstant, lastLoginInstant, roles, authenticationToken, userId, username, usernameStatus);
  }

  public void normalize() {
    authenticationToken = trimToNull(authenticationToken);
    username = trim(username);
    if (data != null) {
      data.normalize();
    }
  }

  public String toString() {
    return ToString.toString(this);
  }
}
