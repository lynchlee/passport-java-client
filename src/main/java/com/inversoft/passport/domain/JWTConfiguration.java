/*
 * Copyright (c) 2016, Inversoft Inc., All Rights Reserved
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

import java.util.Objects;

import org.primeframework.jwt.domain.Algorithm;

/**
 * JWT Configuration.
 *
 * @author Daniel DeGroff
 */
public class JWTConfiguration implements Buildable<JWTConfiguration> {

  public Algorithm algorithm;

  public String privateKey;

  public String publicKey;

  public String secret;

  public JWTConfiguration() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof JWTConfiguration)) {
      return false;
    }
    JWTConfiguration that = (JWTConfiguration) o;
    return Objects.equals(algorithm, that.algorithm) &&
        Objects.equals(privateKey, that.privateKey) &&
        Objects.equals(publicKey, that.publicKey) &&
        Objects.equals(secret, that.secret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(algorithm, privateKey, publicKey, secret);
  }
}
