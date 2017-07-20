/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Map;
import java.util.UUID;

/**
 * @author Tyler Scott
 */
public class Group implements Buildable<Group> {
  public GroupData data = new GroupData();

  public UUID id;

  public String name;

  public Map<UUID, ApplicationRole> roles;

  public Group() {
  }

  public Group(String name) {
    this.name = name;
  }

  public Group(UUID id, String name) {
    this.id = id;
    this.name = name;
  }
}
