/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain;

/**
 * @author Daniel DeGroff
 */
public enum TwoFactorCodeDelivery {
  /**
   * No delivery mechanism provided by Passport. Code must be entered manually from a code generator such as Google Authenticator.
   */
  None,
  /**
   * SMS Push is utilized if available.
   */
  TextMessage
}
