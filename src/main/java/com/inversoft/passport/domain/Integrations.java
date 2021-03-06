/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;

import com.inversoft.json.ToString;

/**
 * Available Integrations
 *
 * @author Daniel DeGroff
 */
public class Integrations implements Buildable<Integrations> {
  public CleanSpeakConfiguration cleanspeak = new CleanSpeakConfiguration();

  public KafkaConfiguration kafka = new KafkaConfiguration();

  public TwilioConfiguration twilio = new TwilioConfiguration();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Integrations that = (Integrations) o;
    return Objects.equals(cleanspeak, that.cleanspeak) &&
        Objects.equals(kafka, that.kafka) &&
        Objects.equals(twilio, that.twilio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cleanspeak, kafka, twilio);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
