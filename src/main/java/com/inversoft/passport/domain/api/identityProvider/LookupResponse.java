/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.identityProvider;

import java.net.URI;

import com.inversoft.json.JacksonConstructor;

/**
 * @author Daniel DeGroff
 */
public class LookupResponse {
  public URI url;

  @JacksonConstructor
  public LookupResponse() {
  }

  public LookupResponse(URI url) {
    this.url = url;
  }
}
