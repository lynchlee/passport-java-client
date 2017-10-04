/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.net.URI;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class IdentityProviderOauth2Configuration implements Buildable<IdentityProviderOauth2Configuration> {
  @JsonProperty("authorization_endpoint")
  public URI authorizationEndpoint;

  @JsonProperty("token_endpoint")
  public URI tokenEndpoint;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IdentityProviderOauth2Configuration)) {
      return false;
    }
    IdentityProviderOauth2Configuration that = (IdentityProviderOauth2Configuration) o;
    return Objects.equals(authorizationEndpoint, that.authorizationEndpoint) &&
        Objects.equals(tokenEndpoint, that.tokenEndpoint);
  }


  @Override
  public int hashCode() {
    return Objects.hash(authorizationEndpoint, tokenEndpoint);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
