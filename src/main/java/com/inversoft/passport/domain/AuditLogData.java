/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.LinkedHashMap;
import java.util.Map;

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
}
