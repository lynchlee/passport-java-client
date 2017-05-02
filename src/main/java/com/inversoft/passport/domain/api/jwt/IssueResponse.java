/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.jwt;

import com.inversoft.json.JacksonConstructor;

/**
 * @author Daniel DeGroff
 */
public class IssueResponse {
  public String refreshToken;

  public String token;

  @JacksonConstructor
  public IssueResponse() {
  }

  public IssueResponse(String token) {
    this.token = token;
  }
}
