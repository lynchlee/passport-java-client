/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * Twillio Service Configuration.
 *
 * @author Daniel DeGroff
 */
public class TwillioConfiguration extends Enableable implements Buildable<TwillioConfiguration> {
  public String accountSID;

  public String apiURL = "https://api.twilio.com";

  public String authToken;

  public String fromPhoneNumber;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TwillioConfiguration that = (TwillioConfiguration) o;
    return Objects.equals(accountSID, that.accountSID) &&
        Objects.equals(authToken, that.authToken) &&
        Objects.equals(apiURL, that.apiURL) &&
        Objects.equals(enabled, that.enabled) &&
        Objects.equals(fromPhoneNumber, that.fromPhoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountSID, authToken, apiURL, fromPhoneNumber);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
