/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class MaximumPasswordAge extends Enableable {
  public int days;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaximumPasswordAge that = (MaximumPasswordAge) o;
    return Objects.equals(days, that.days) &&
        Objects.equals(enabled, that.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(days, enabled);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
