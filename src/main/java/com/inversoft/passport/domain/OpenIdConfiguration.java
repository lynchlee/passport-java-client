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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OpenID Configuration as described by the <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata">OpenID
 * Provider Metadata</a>.
 *
 * @author Daniel DeGroff
 */
public class OpenIdConfiguration implements Buildable<OpenIdConfiguration> {
  /**
   * <p> <strong>REQUIRED</strong>. URL of the OP's OAuth 2.0 Authorization Endpoint <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#OpenID.Core">[OpenID.Core]</a></p>
   */
  @JsonProperty("authorization_endpoint")
  public String authorizationEndpoint;

  /**
   * <p> <strong>RECOMMENDED</strong>. JSON array containing a list of the Claim Names of the Claims that the OpenID Provider MAY be able to
   * supply values for. Note that for privacy or other reasons, this might not be an exhaustive list. </p>
   */
  @JsonProperty("claims_supported")
  public List<String> claimsSupported;

  /**
   * <p> <strong>REQUIRED</strong>. URL using the <code>https</code> scheme with no query or fragment component that the OP asserts as its
   * Issuer Identifier. If Issuer discovery is supported (see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#IssuerDiscovery">Section
   * 2</a>), this value MUST be identical to the issuer value returned by WebFinger. This also MUST be identical to the <code>iss</code>
   * Claim value in ID Tokens issued from this Issuer. </p>
   */
  public String issuer;

  /**
   * <p> <strong>REQUIRED</strong>. URL of the OP's JSON Web Key Set <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#JWK">[JWK]</a>
   * document. This contains the signing key(s) the RP uses to validate signatures from the OP. The JWK Set MAY also contain the Server's
   * encryption key(s), which are used by RPs to encrypt requests to the Server. When both signing and encryption keys are made available, a
   * use (Key Use) parameter value is <strong>REQUIRED</strong> for all keys in the referenced JWK Set to indicate each key's intended
   * usage. Although some algorithms allow the same key to be used for both signatures and encryption, doing so is NOT
   * <strong>RECOMMENDED</strong>, as it is less secure. The JWK x5c parameter MAY be used to provide X.509 representations of keys
   * provided. When used, the bare key values MUST still be present and MUST match those in the certificate. </p>
   */
  @JsonProperty("jwks_uri")
  public String jwksURI;

  /**
   * <p> URL of the OP's OAuth 2.0 Token Endpoint <a href="https://openid.net/specs/openid-connect-discovery-1_0.html#OpenID.Core">[OpenID.Core]</a>.
   * This is <strong>REQUIRED</strong> unless only the Implicit Flow is used. </p>
   */
  @JsonProperty("token_endpoint")
  public String tokenEndpoint;
}
