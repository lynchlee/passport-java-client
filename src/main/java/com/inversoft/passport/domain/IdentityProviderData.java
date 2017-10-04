/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.HashMap;
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
  public Map<String, String> keys = new HashMap<>();

  public IdentityProviderOauth2Configuration oauth2 = new IdentityProviderOauth2Configuration();

  public String uniqueIdentityClaim;

  public UniqueIdentityClaimType uniqueIdentityClaimType;

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
        Objects.equals(oauth2, that.oauth2) &&
        Objects.equals(uniqueIdentityClaim, that.uniqueIdentityClaim) &&
        Objects.equals(uniqueIdentityClaimType, that.uniqueIdentityClaimType);

  }

  @Override
  public int hashCode() {
    return Objects.hash(keys, oauth2, uniqueIdentityClaim, uniqueIdentityClaimType);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
