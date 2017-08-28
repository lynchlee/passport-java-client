/*
 * Copyright (c) 2015-2017, Inversoft Inc., All Rights Reserved
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

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.Buildable;
import com.inversoft.passport.domain.jwt.RefreshToken.MetaData;

/**
 * Login API request object.
 *
 * @author Seth Musselman
 */
public class LoginRequest implements Buildable<LoginRequest> {
  public UUID applicationId;

  /**
   * Optional parameter on the Login Request. When logging in with an application Id and a device the response will
   * contain a refresh token.
   */
  public String device;

  public String ipAddress;

  public String loginId;

  /**
   * Optional parameter allowing the caller to specify meta data about the login. Device information, etc.
   */
  public MetaData metaData;

  public boolean noJWT;

  public String password;

  @JacksonConstructor
  public LoginRequest() {
  }

  public LoginRequest(UUID applicationId, String loginId, String password, String ipAddress) {
    this.applicationId = applicationId;
    this.ipAddress = ipAddress;
    this.loginId = loginId;
    this.password = password;
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