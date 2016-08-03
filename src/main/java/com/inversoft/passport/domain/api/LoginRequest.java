/*
 * Copyright (c) 2015, Inversoft Inc., All Rights Reserved
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

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inversoft.json.JacksonConstructor;

/**
 * Login API request object.
 *
 * @author Seth Musselman
 */
public class LoginRequest {
  public UUID applicationId;

  public String loginId;

  public String password;

  @JacksonConstructor
  public LoginRequest() {
  }

  public LoginRequest(UUID applicationId, String loginId, String password) {
    this.applicationId = applicationId;
    this.loginId = loginId;
    this.password = password;
  }

  public String getEmail() {
    return loginId;
  }

  public void setEmail(String email) {
    this.loginId = email;
  }

  public String getUsername() {
    return loginId;
  }

  public void setUsername(String username) {
    this.loginId = username;
  }
}