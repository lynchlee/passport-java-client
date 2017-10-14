/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.inversoft.passport.domain.api;

import java.util.List;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.Group;

/**
 * Group API request object.
 *
 * @author Daniel DeGroff
 */
public class GroupRequest {
  public Group group;

  public List<UUID> roleIds;

  @JacksonConstructor
  public GroupRequest() {
  }

  public GroupRequest(Group group, List<UUID> roleIds) {
    this.group = group;
    this.roleIds = roleIds;
  }

  public GroupRequest(Group group) {
    this.group = group;
  }
}
