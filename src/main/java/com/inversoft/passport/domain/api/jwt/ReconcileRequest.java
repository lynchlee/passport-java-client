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

  public ReconcileRequest(UUID applicationId, String encodedJWT) {
    this.applicationId = applicationId;
    this.encodedJWT = encodedJWT;
  }

  public ReconcileRequest(UUID applicationId, String encodedJWT, String ipAddress) {
    this.applicationId = applicationId;
    this.encodedJWT = encodedJWT;
    this.ipAddress = ipAddress;
  }
}
