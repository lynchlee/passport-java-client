/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.client;

import com.inversoft.passport.domain.User;
import org.testng.annotations.Test;

/**
 * @author Brian Pontarelli
 */
public class LambdaDelegateTest {
  @Test
  public void all() {
    PassportClient client = new PassportClient(null, null);
    LambdaDelegate delegate = new LambdaDelegate(client, null, null);

    User user = delegate.execute(c -> c.retrieveUser(null)).user;
  }
}
