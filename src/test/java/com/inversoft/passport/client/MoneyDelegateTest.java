/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.client;

import com.inversoft.passport.domain.User;
import org.testng.annotations.Test;

/**
 * @author Brian Pontarelli
 */
public class MoneyDelegateTest {
  @Test
  public void all() {
    PassportClient client = new PassportClient(null, null);
    MoneyDelegate delegate = new MoneyDelegate(null, null);

    User user = delegate.delegate(() -> client.retrieveUser(null)).user;
  }
}
