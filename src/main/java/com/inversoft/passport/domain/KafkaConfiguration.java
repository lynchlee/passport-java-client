/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

import java.util.Objects;
import java.util.TreeMap;

import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class KafkaConfiguration extends Enableable implements Buildable<KafkaConfiguration>, Integration {
  public String defaultTopic;

  public TreeMap<String, String> producer = new TreeMap<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KafkaConfiguration that = (KafkaConfiguration) o;
    return super.equals(o) &&
        Objects.equals(defaultTopic, that.defaultTopic) &&
        Objects.equals(producer, that.producer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), defaultTopic, producer);
  }

  public void normalize() {
    if (!producer.containsKey("bootstrap.servers")) {
      producer.put("bootstrap.servers", "localhost:9092");
      producer.put("max.block.ms", "5000");
      producer.put("request.timeout.ms", "2000");
    }
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
