/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.GroupMember;

/**
 * Group Member Response
 *
 * @author Daniel DeGroff
 */
public class MemberResponse {
  public Map<UUID, List<GroupMember>> members;

  @JacksonConstructor
  public MemberResponse() {
  }

  public MemberResponse(Map<UUID, List<GroupMember>> members) {
    this.members = members;
  }
}
