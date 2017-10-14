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
package com.inversoft.passport.domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;

/**
 * @author Tyler Scott
 */
public class Group implements Buildable<Group> {
  public GroupData data;

  public UUID id;

  public String name;

  public Map<UUID, List<ApplicationRole>> roles = new HashMap<>();

  @JacksonConstructor
  public Group() {
  }

  public Group(String name) {
    this.name = name;
  }

  public Group(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Group group = (Group) o;
    roles.values().forEach(c -> c.sort(Comparator.comparing(r -> r.id)));
    group.roles.values().forEach(c -> c.sort(Comparator.comparing(r -> r.id)));
    return Objects.equals(data, group.data) &&
        Objects.equals(name, group.name) &&
        Objects.equals(roles, group.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, id, name, roles);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
