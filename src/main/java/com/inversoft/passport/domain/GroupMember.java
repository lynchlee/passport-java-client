/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import com.inversoft.json.ToString;

/**
 * A User's membership into a Group
 *
 * @author Daniel DeGroff
 */
public class GroupMember implements Buildable<GroupMember> {
  public GroupData data;

  public UUID groupId;

  public UUID id;

  public ZonedDateTime insertInstant;

  public UUID userId;

  public GroupMember() {
  }

  public GroupMember(GroupMember member) {
    this.data = member.data;
    this.groupId = member.groupId;
    this.id = member.id;
    this.insertInstant = member.insertInstant;
    this.userId = member.userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupMember that = (GroupMember) o;
    return Objects.equals(data, that.data) &&
        Objects.equals(groupId, that.groupId) &&
        Objects.equals(insertInstant, that.insertInstant) &&
        Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, groupId, id, insertInstant, userId);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
