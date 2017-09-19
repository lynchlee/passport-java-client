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
  public int days = 180;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaximumPasswordAge that = (MaximumPasswordAge) o;
    return super.equals(o) &&
        Objects.equals(days, that.days);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), days);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
