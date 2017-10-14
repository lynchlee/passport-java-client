/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
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
