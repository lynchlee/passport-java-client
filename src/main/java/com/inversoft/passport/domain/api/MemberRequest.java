/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import java.util.List;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.GroupMember;

/**
 * Group Member Request
 *
 * @author Daniel DeGroff
 */
public class MemberRequest {
  public List<UUID> memberIds;

  public List<GroupMember> members;

  @JacksonConstructor
  public MemberRequest() {
  }

  public MemberRequest(List<GroupMember> members) {
    this.members = members;
  }
}
