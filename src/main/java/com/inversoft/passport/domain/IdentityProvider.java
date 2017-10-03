/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class IdentityProvider implements Buildable<IdentityProvider> {
  @JsonUnwrapped
  public IdentityProviderData data = new IdentityProviderData();

  public Set<String> domains = new HashSet<>();

  public UUID id;

  public String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IdentityProvider)) {
      return false;
    }
    IdentityProvider that = (IdentityProvider) o;
    return Objects.equals(data, that.data) &&
        Objects.equals(domains, that.domains) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, domains, name);
  }

  public void normalize() {
    // Normalize Line returns in the PEM encoded keys
    if (data.keys != null) {
      data.keys.stream().filter(p -> p.encodedKey != null)
               .forEach(key -> key.encodedKey = key.encodedKey.replace("\r\n", "\n").replace("\r", "\n"));
    }
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
