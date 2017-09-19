/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.util.Objects;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;
import com.inversoft.passport.domain.Buildable;

/**
 * @author Daniel DeGroff
 */
public class TestEvent extends BaseEvent implements Buildable<TestEvent> {
  public String message;

  @JacksonConstructor
  public TestEvent() {
    message = "Example Passport Event.";
  }

  public TestEvent(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestEvent that = (TestEvent) o;
    return super.equals(o) &&
        Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), message);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }

  @Override
  public EventType type() {
    return EventType.Test;
  }
}
