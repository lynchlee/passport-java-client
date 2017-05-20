/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class AuditLogData implements Buildable<AuditLogData> {
  public final Map<String, Object> attributes = new LinkedHashMap<>();

  public Object newValue;

  public Object oldValue;

  public AuditLogData() {
  }

  public AuditLogData(Object oldValue, Object newValue) {
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuditLogData that = (AuditLogData) o;
    return Objects.equals(attributes, that.attributes) &&
        Objects.equals(newValue, that.newValue) &&
        Objects.equals(oldValue, that.oldValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributes, newValue, oldValue);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
