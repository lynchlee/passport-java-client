/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class IdentityProviderData implements Buildable<IdentityProviderData> {
  /**
   * Map of keys used for signature validation for this provider. Key Id to PEM encoded certificate of public key.
   */
  public Map<String, String> keys;

  public URI url;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof com.inversoft.passport.domain.IdentityProviderData)) {
      return false;
    }
    com.inversoft.passport.domain.IdentityProviderData that = (com.inversoft.passport.domain.IdentityProviderData) o;
    return Objects.equals(keys, that.keys) &&
        Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keys, url);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
