/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.Integrations;

/**
 * The Integration Request
 *
 * @author Daniel DeGroff
 */
public class IntegrationRequest {
  public Integrations integrations;

  @JacksonConstructor
  public IntegrationRequest() {
  }

  public IntegrationRequest(Integrations integrations) {
    this.integrations = integrations;
  }
}
