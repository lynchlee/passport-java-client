/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.identityProvider;

import java.net.URI;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.IdentityProvider;

/**
 * @author Daniel DeGroff
 */
public class LookupResponse {
  public IdentityProvider identityProvider;

  @JacksonConstructor
  public LookupResponse() {
  }

  public LookupResponse(IdentityProvider identityProvider) {
    this.identityProvider = identityProvider;
  }
}
