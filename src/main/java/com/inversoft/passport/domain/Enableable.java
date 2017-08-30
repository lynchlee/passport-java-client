/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;

/**
 * Something that can be enabled, and thus also disabled.
 *
 * @author Daniel DeGroff
 */
public abstract class Enableable {
  public boolean enabled;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Enableable)) {
      return false;
    }
    Enableable that = (Enableable) o;
    return enabled == that.enabled;
  }

  @Override
  public int hashCode() {
    return Objects.hash(enabled);
  }
}
