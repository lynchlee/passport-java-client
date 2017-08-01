/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.GroupMember;

/**
 * Group Member Request
 *
 * @author Daniel DeGroff
 */
public class MemberRequest {
  public Map<UUID, List<GroupMember>> members;

  @JacksonConstructor
  public MemberRequest() {
  }

  public MemberRequest(Map<UUID, List<GroupMember>> members) {
    this.members = members;
  }

  public MemberRequest(UUID groupId, List<GroupMember> members) {
    this.members = new HashMap<>(1);
    this.members.put(groupId, members);
  }
}
