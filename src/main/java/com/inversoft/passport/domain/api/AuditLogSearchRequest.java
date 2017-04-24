/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.search.AuditLogSearchCriteria;

/**
 * @author Brian Pontarelli
 */
public class AuditLogSearchRequest {
  public AuditLogSearchCriteria search;

  @JacksonConstructor
  public AuditLogSearchRequest() {
  }

  public AuditLogSearchRequest(AuditLogSearchCriteria search) {
    this.search = search;
  }
}
