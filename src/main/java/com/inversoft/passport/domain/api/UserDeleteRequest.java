/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.List;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;

/**
 * User API delete request object.
 *
 * @author Daniel DeGroff
 */
public class UserDeleteRequest {
  public boolean hardDelete;

  public List<UUID> userIds;

  @JacksonConstructor
  public UserDeleteRequest() {
  }

  public UserDeleteRequest(List<UUID> userIds) {
    this.userIds = userIds;
  }

  public UserDeleteRequest(List<UUID> userIds, boolean hardDelete) {
    this.hardDelete = hardDelete;
    this.userIds = userIds;
  }
}
