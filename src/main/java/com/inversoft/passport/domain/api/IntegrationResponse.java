/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.Integrations;

/**
 * The Integration Response
 *
 * @author Daniel DeGroff
 */
public class IntegrationResponse {
  public Integrations integrations;

  @JacksonConstructor
  public IntegrationResponse() {
  }

  public IntegrationResponse(Integrations integrations) {
    this.integrations = integrations;
  }
}
