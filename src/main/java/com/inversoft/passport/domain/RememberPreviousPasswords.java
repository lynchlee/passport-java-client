/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class RememberPreviousPasswords extends Enableable {
  public int count;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RememberPreviousPasswords that = (RememberPreviousPasswords) o;
    return Objects.equals(count, that.count) &&
        Objects.equals(enabled, that.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, enabled);
  }


  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
