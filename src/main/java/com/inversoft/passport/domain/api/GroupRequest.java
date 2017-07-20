/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.Group;

/**
 * Group API request object.
 *
 * @author Daniel DeGroff
 */
public class GroupRequest {
  public Group group;

  @JacksonConstructor
  public GroupRequest() {
  }

  public GroupRequest(Group group) {
    this.group = group;
  }
}
