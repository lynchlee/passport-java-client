/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class IdentityProviderData implements Buildable<IdentityProviderData> {
  public IdentityProviderConfiguration configuration = new IdentityProviderConfiguration();

  public List<IdentityProviderKey> keys = new ArrayList<>();

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
    return Objects.equals(configuration, that.configuration) &&
        Objects.equals(keys, that.keys) &&
        Objects.equals(uniqueIdentityClaim, that.uniqueIdentityClaim) &&
        Objects.equals(uniqueIdentityClaimType, that.uniqueIdentityClaimType);

  }

  @Override
  public int hashCode() {
    return Objects.hash(configuration, keys, uniqueIdentityClaim, uniqueIdentityClaimType);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
