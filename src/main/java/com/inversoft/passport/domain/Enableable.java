/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import com.inversoft.json.ToString;

/**
 * Something that can be enabled, and thus also disabled.
 *
 * @author Daniel DeGroff
 */
public abstract class Enableable {
  public boolean enabled;

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
