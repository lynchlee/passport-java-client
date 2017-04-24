/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.List;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.AuditLog;
import com.inversoft.passport.domain.search.SearchResults;

/**
 * Audit log response.
 *
 * @author Brian Pontarelli
 */
public class AuditLogSearchResponse {
  public List<AuditLog> auditLogs;

  public long total;

  @JacksonConstructor
  public AuditLogSearchResponse() {
  }

  public AuditLogSearchResponse(SearchResults<AuditLog> searchResults) {
    this.auditLogs = searchResults.results;
    this.total = searchResults.total;
  }
}
