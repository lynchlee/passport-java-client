/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.IdentityProvider;

/**
 * @author Daniel DeGroff
 */
public class IdentityProviderResponse {
  public IdentityProvider identityProvider;

  @JacksonConstructor
  public IdentityProviderResponse() {
  }

  public IdentityProviderResponse(IdentityProvider identityProvider) {
    this.identityProvider = identityProvider;
  }
}
