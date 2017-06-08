/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class MinimumPasswordAge extends Enableable {
  public int seconds = 30;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MinimumPasswordAge that = (MinimumPasswordAge) o;
    return Objects.equals(enabled, that.enabled) &&
        Objects.equals(seconds, that.seconds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enabled, seconds);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
