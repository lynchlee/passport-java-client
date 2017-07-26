/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.List;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.Group;

/**
 * Group API response object.
 *
 * @author Daniel DeGroff
 */
public class GroupResponse {
  public Group group;

  public List<Group> groups;

  @JacksonConstructor
  public GroupResponse() {
  }

  public GroupResponse(Group group) {
    this.group = group;
  }

  public GroupResponse(List<Group> groups) {
    this.groups = groups;
  }
}
