/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class IdentityProviderKey implements Buildable<IdentityProviderKey> {
  /**
   * PEM Encoded key.
   */
  public String encodedKey;

  public String keyId;

  public IdentityProviderKey(String keyId, String encodedKey) {
    this.keyId = keyId;
    this.encodedKey = encodedKey;
  }

  @JacksonConstructor
  public IdentityProviderKey() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IdentityProviderKey)) {
      return false;
    }
    IdentityProviderKey that = (IdentityProviderKey) o;
    return Objects.equals(keyId, that.keyId) &&
        Objects.equals(encodedKey, that.encodedKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keyId, encodedKey);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
