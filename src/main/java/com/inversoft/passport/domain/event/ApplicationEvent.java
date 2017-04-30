/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.event;

import java.util.List;
import java.util.UUID;

/**
 * Events that are bound to applications.
 *
 * @author Brian Pontarelli
 */
public interface ApplicationEvent {
  /**
   * @return The application ids for the event.
   */
  List<UUID> applicationIds();
}
