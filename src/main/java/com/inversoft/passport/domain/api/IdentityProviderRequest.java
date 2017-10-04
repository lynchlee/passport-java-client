/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.IdentityProvider;

/**
 * @author Daniel DeGroff
 */
public class IdentityProviderRequest {
  public IdentityProvider identityProvider;

  @JacksonConstructor
  public IdentityProviderRequest() {
  }

  public IdentityProviderRequest(IdentityProvider identityProvider) {
    this.identityProvider = identityProvider;
  }
}
