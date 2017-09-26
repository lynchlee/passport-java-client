/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class IdentityProvider implements Buildable<IdentityProvider> {
  @JsonUnwrapped
  public IdentityProviderData data = new IdentityProviderData();

  public List<String> domains = new ArrayList<>();

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

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
