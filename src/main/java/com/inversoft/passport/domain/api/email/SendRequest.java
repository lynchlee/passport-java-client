/*
 * Copyright (c) 2015-2016, Inversoft Inc., All Rights Reserved
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
package com.inversoft.passport.domain.api.email;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.lang.ObjectTools;

/**
 * @author Daniel DeGroff
 */
public class SendRequest {
  public List<String> bccAddresses;

  public List<String> ccAddresses;

  public Map<String, Object> requestData;

  public List<UUID> userIds;

  @JacksonConstructor
  public SendRequest() {
  }

  public SendRequest(List<UUID> userIds, List<String> ccAddresses, List<String> bccAddresses,
                     Map<String, Object> requestData) {
    this.userIds = userIds;
    this.ccAddresses = ccAddresses;
    this.bccAddresses = bccAddresses;
    this.requestData = requestData;
  }

  public SendRequest(List<UUID> userIds, Map<String, Object> requestData) {
    this.userIds = userIds;
    this.requestData = requestData;
  }

  public SendRequest normalize() {
    requestData = ObjectTools.defaultIfNull(requestData, HashMap<String, Object>::new);
    userIds = ObjectTools.defaultIfNull(userIds, ArrayList<UUID>::new);
    ccAddresses = ObjectTools.defaultIfNull(ccAddresses, ArrayList<String>::new);
    bccAddresses = ObjectTools.defaultIfNull(bccAddresses, ArrayList<String>::new);
    return this;
  }
}
