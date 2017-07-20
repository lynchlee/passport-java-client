/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A User's membership into a Group
 *
 * @author Daniel DeGroff
 */
public class GroupMembership implements Buildable<GroupMembership> {
  public GroupData data;

  public UUID id;

  public ZonedDateTime insertInstant;

  public UUID userId;
}
