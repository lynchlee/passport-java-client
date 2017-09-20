/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class IdentityProvider implements Buildable<IdentityProvider> {
  public List<String> domains;

  public String keyId;

  public String name;

  public String publicKey;

  public URI url;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IdentityProvider)) {
      return false;
    }
    IdentityProvider that = (IdentityProvider) o;
    return Objects.equals(domains, that.domains) &&
        Objects.equals(keyId, that.keyId) &&
        Objects.equals(name, that.name) &&
        Objects.equals(publicKey, that.publicKey) &&
        Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(domains, keyId, name, publicKey, url);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
