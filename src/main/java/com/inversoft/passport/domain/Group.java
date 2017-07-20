/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.List;
import java.util.UUID;

/**
 * @author Tyler Scott
 */
public class Group {
  public UUID id;

  public String name;

  public List<ApplicationRole> roles;

  public Group(String name) {
    this.name = name;
  }
}
