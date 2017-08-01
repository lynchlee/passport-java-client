/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;

/**
 * Group Member Delete Request
 *
 * @author Daniel DeGroff
 */
public class MemberDeleteRequest {
  public List<UUID> memberIds;

  public Map<UUID, List<UUID>> members;

  @JacksonConstructor
  public MemberDeleteRequest() {
  }

  public MemberDeleteRequest(List<UUID> memberIds) {
    this.memberIds = memberIds;
  }

  public MemberDeleteRequest(Map<UUID, List<UUID>> members) {
    this.members = members;
  }
}
