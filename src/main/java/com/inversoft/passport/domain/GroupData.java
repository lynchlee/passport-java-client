/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Storage for additional data about the group.
 *
 * @author Daniel DeGroff
 */
public class GroupData implements Buildable<GroupData> {
  public final Map<String, Object> attributes = new LinkedHashMap<>();

  public GroupData() {
  }

  public GroupData(Map<String, Object> attributes) {
    if (attributes != null) {
      this.attributes.putAll(attributes);
    }
  }

  public GroupData(GroupData data) {
    this.attributes.putAll(data.attributes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GroupData)) {
      return false;
    }
    GroupData userData = (GroupData) o;
    return Objects.equals(attributes, userData.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributes);
  }
}
