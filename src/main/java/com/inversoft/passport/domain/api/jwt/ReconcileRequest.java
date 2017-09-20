/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.jwt;

import com.inversoft.json.JacksonConstructor;

/**
 * @author Daniel DeGroff
 */
public class ReconcileRequest {

  public String encodedJWT;

  @JacksonConstructor
  public ReconcileRequest() {
  }

  public ReconcileRequest(String encodedJWT) {
    this.encodedJWT = encodedJWT;
  }
}
