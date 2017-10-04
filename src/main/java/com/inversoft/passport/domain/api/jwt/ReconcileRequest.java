/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api.jwt;

import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.Buildable;
import com.inversoft.passport.domain.jwt.RefreshToken.MetaData;

/**
 * @author Daniel DeGroff
 */
public class ReconcileRequest implements Buildable<ReconcileRequest> {
  public UUID applicationId;

  public String device;

  public String encodedJWT;

  public UUID identityProviderId;

  public String ipAddress;

  public MetaData metaData;

  @JacksonConstructor
  public ReconcileRequest() {
  }

  public ReconcileRequest(UUID identityProviderId, UUID applicationId, String encodedJWT) {
    this.identityProviderId = identityProviderId;
    this.applicationId = applicationId;
    this.encodedJWT = encodedJWT;
  }

  public ReconcileRequest(UUID identityProviderId, UUID applicationId, String encodedJWT, String ipAddress) {
    this.identityProviderId = identityProviderId;
    this.applicationId = applicationId;
    this.encodedJWT = encodedJWT;
    this.ipAddress = ipAddress;
  }
}
