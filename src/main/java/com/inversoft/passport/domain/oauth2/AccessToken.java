/*
 * Copyright (c) 2015-2017, Inversoft Inc., All Rights Reserved
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
package com.inversoft.passport.domain.oauth2;


import java.net.URI;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class AccessToken implements OAuthResponse {

  @JsonIgnore
  public String clientId;

  /**
   * The lifetime in seconds of the access token. The time the token will expire from the time the response was
   * generated.
   */
  @JsonProperty("expires_in")
  public int expiresIn;

  @JsonIgnore
  public URI redirectURI;

  public String state;

  /**
   * The access token issued by the authorization server.
   */
  @JsonProperty("access_token")
  public String token;

  /**
   * Token type.
   */
  @JsonProperty("token_type")
  public TokenType tokenType;

  public UUID userId;

  @JsonIgnore
  private ZonedDateTime createInstant;

  @JacksonConstructor
  public AccessToken() {
  }

  public AccessToken(String token, String clientId, int expiresIn, URI redirectURI, String state,
                     TokenType tokenType,
                     UUID userId) {
    this.clientId = clientId;
    this.createInstant = ZonedDateTime.now(ZoneOffset.UTC);
    this.expiresIn = expiresIn;
    this.redirectURI = redirectURI;
    this.state = state;
    this.token = token;
    this.tokenType = tokenType;
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessToken that = (AccessToken) o;
    return Objects.equals(clientId, that.clientId) &&
        Objects.equals(createInstant, that.createInstant) &&
        Objects.equals(expiresIn, that.expiresIn) &&
        Objects.equals(redirectURI, that.redirectURI) &&
        Objects.equals(state, that.state) &&
        Objects.equals(token, that.token) &&
        Objects.equals(tokenType, that.tokenType) &&
        Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, createInstant, expiresIn, redirectURI, state, token, tokenType, userId);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
