/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.jwt;

import java.net.URI;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;

/**
 * @author Daniel DeGroff
 */
public class ReconcileRequest {
  public UUID applicationId;

  public String encodedJWT;

  public String ipAddress;

  public URI url;

  @JacksonConstructor
  public ReconcileRequest() {
  }

  public ReconcileRequest(UUID applicationId, URI url, String encodedJWT) {
    this.applicationId = applicationId;
    this.url = url;
    this.encodedJWT = encodedJWT;
  }
}
