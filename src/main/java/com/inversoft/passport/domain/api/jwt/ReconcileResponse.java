/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.jwt;

import com.inversoft.json.JacksonConstructor;

/**
 * @author Daniel DeGroff
 */
public class ReconcileResponse {

  public String token;

  public String refreshToken;


  @JacksonConstructor
  public ReconcileResponse() {
  }

  public ReconcileResponse(String token) {
    this.token = token;
  }
}
