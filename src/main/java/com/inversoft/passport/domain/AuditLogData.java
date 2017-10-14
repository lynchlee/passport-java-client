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

  public String reason;

  public AuditLogData() {
  }

  public AuditLogData(Object oldValue, Object newValue) {
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  public AuditLogData(Object oldValue, Object newValue, String reason) {
    this.oldValue = oldValue;
    this.newValue = newValue;
    this.reason = reason;
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
        Objects.equals(oldValue, that.oldValue) &&
        Objects.equals(reason, that.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributes, newValue, oldValue, reason);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
