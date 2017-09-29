/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.jwt;

import java.net.URI;

import com.inversoft.json.JacksonConstructor;

/**
 * @author Daniel DeGroff
 */
public class ReconcileRequest {

  public String encodedJWT;

  public URI url;

  @JacksonConstructor
  public ReconcileRequest() {
  }

  public ReconcileRequest(URI url, String encodedJWT) {
    this.url = url;
    this.encodedJWT = encodedJWT;
  }
}
