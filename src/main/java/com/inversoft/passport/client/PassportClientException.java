/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.client;

import com.inversoft.error.Errors;

/**
 * @author Brian Pontarelli
 */
public class PassportClientException extends RuntimeException {
  public final Errors errors;

  public PassportClientException(String message) {
    super(message);
    this.errors = null;
  }

  public PassportClientException(Errors errors) {
    this.errors = errors;
  }

  public PassportClientException(Throwable cause) {
    super(cause);
    this.errors = null;
  }
}
