/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;
import java.util.Properties;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class KafkaConfiguration extends Enableable implements Buildable<KafkaConfiguration> {
  public String defaultTopic;

  public Properties producer = new Properties();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KafkaConfiguration that = (KafkaConfiguration) o;
    return Objects.equals(defaultTopic, that.defaultTopic) &&
        Objects.equals(enabled, that.enabled) &&
        Objects.equals(producer, that.producer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(defaultTopic, enabled, producer);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
