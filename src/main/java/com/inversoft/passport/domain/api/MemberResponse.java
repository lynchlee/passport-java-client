/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.GroupMember;

/**
 * Group Member Response
 *
 * @author Daniel DeGroff
 */
public class MemberResponse {
  public GroupMember member;

  @JacksonConstructor
  public MemberResponse() {
  }

  public MemberResponse(GroupMember member) {
    this.member = member;
  }
}
