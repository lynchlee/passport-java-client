/*
 * Copyright (c) 2015-2017, Inversoft Inc., All Rights Reserved
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
import java.util.Objects;

import com.inversoft.passport.domain.util.Normalizer;

/**
 * An audit log.
 *
 * @author Brian Pontarelli
 */
public class AuditLog implements Buildable<AuditLog> {
  public AuditLogData data;

  public ZonedDateTime insertInstant;

  public String insertUser;

  public String message;

  public AuditLog() {
  }

  public AuditLog(String insertUser, String message) {
    this(insertUser, message, null);
  }

  public AuditLog(String insertUser, String message, AuditLogData data) {
    this.data = data;
    this.insertUser = insertUser;
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AuditLog)) {
      return false;
    }
    AuditLog auditLog = (AuditLog) o;
    return Objects.equals(data, auditLog.data) &&
        Objects.equals(insertInstant, auditLog.insertInstant) &&
        Objects.equals(insertUser, auditLog.insertUser) &&
        Objects.equals(message, auditLog.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, insertInstant, insertUser, message);
  }

  public void normalize() {
    insertUser = Normalizer.trim(insertUser);
    message = Normalizer.trim(message);
  }
}
