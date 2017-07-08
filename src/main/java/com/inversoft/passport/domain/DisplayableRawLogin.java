/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import com.inversoft.json.ToString;

/**
 * A displayable raw login that includes application name and user loginId.
 *
 * @author Brian Pontarelli
 */
public class DisplayableRawLogin extends RawLogin implements Buildable<DisplayableRawLogin> {
  public String applicationName;

  public String loginId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DisplayableRawLogin)) {
      return false;
    }
    DisplayableRawLogin rawLogin = (DisplayableRawLogin) o;
    return Objects.equals(applicationId, rawLogin.applicationId) &&
        Objects.equals(instant, rawLogin.instant) &&
        Objects.equals(ipAddress, rawLogin.ipAddress) &&
        Objects.equals(userId, rawLogin.userId) &&
        Objects.equals(applicationName, rawLogin.applicationName) &&
        Objects.equals(loginId, rawLogin.loginId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationId, instant, ipAddress, userId, applicationName, loginId);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
