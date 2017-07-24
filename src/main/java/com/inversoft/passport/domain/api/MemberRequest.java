/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.GroupMember;

/**
 * Group Member Request
 *
 * @author Daniel DeGroff
 */
public class MemberRequest {
  public GroupMember member;

  @JacksonConstructor
  public MemberRequest() {
  }

  public MemberRequest(GroupMember member) {
    this.member = member;
  }
}
