/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.List;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.IdentityProvider;

/**
 * @author Daniel DeGroff
 */
public class IdentityProviderResponse {
  public IdentityProvider identityProvider;

  public List<IdentityProvider> identityProviders;

  @JacksonConstructor
  public IdentityProviderResponse() {
  }

  public IdentityProviderResponse(List<IdentityProvider> identityProviders) {
    this.identityProviders = identityProviders;
  }

  public IdentityProviderResponse(IdentityProvider identityProvider) {
    this.identityProvider = identityProvider;
  }
}
