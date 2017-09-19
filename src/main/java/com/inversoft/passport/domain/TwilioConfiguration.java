/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.net.URI;
import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * Twilio Service Configuration.
 *
 * @author Daniel DeGroff
 */
public class TwilioConfiguration extends Enableable implements Buildable<TwilioConfiguration>, Integration {
  public String accountSID;

  public String authToken;

  public String fromPhoneNumber;

  public String messagingServiceSid;

  public URI url = URI.create("https://api.twilio.com");

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TwilioConfiguration that = (TwilioConfiguration) o;
    return super.equals(o) &&
        Objects.equals(accountSID, that.accountSID) &&
        Objects.equals(authToken, that.authToken) &&
        Objects.equals(fromPhoneNumber, that.fromPhoneNumber) &&
        Objects.equals(messagingServiceSid, that.messagingServiceSid) &&
        Objects.equals(url, that.url);

  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), accountSID, authToken, fromPhoneNumber, messagingServiceSid, url);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
