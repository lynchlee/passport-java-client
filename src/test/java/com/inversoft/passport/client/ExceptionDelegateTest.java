/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.client;

import com.inversoft.passport.domain.User;
import org.testng.annotations.Test;

/**
 * @author Brian Pontarelli
 */
public class ExceptionDelegateTest {
  @Test
  public void all() {
    PassportClient client = new PassportClient(null, null);
    ExceptionDelegate delegate = new ExceptionDelegate();

    User user = delegate.execute(() -> client.retrieveUser(null)).user;
  }
}
