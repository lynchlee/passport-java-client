/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.inversoft.json.ToString;

/**
 * @author Tyler Scott
 */
public class Group implements Buildable<Group> {
  public GroupData data;

  public UUID id;

  public String name;

  public Map<UUID, ApplicationRole> roles;

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
    return Objects.equals(data, group.data) &&
        Objects.equals(id, group.id) &&
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
