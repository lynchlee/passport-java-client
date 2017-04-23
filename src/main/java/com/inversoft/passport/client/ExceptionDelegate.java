/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.client;

import java.util.function.Supplier;

import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;

/**
 * This is a helper delegate that makes it simpler to invoke the {@link PassportClient} methods and automatically deals
 * with the {@link ClientResponse} object for you.
 *
 * @author Brian Pontarelli
 */
public class ExceptionDelegate {
  public ExceptionDelegate() {
  }

  /**
   * Executes the delegated PassportClient call using the given supplier (to make the API call) and handles the response
   * by returning the success response if the status code was 2xx. If the status code was 404, this returns null. For
   * all other status codes, this throws a {@link PassportClientException}
   *
   * @param supplier The supplier that should invoke the PassportClient method.
   * @param <T>      The success response type.
   * @param <U>      The error response type.
   * @return The success response type if the API call was successful.
   * @throws PassportClientException If the status code was anything exception 2xx or 404 or there was a problem calling
   *                                 the API (network or something similar).
   */
  @SuppressWarnings("unchecked")
  public <T, U> T execute(Supplier<ClientResponse<T, U>> supplier) {
    ClientResponse<T, U> response = supplier.get();
    if (response.wasSuccessful()) {
      return response.successResponse;
    } else if (response.status == 404) {
      return null;
    } else if (response.errorResponse != null && response.errorResponse instanceof Errors) {
      throw new PassportClientException((Errors) response.errorResponse);
    } else if (response.errorResponse != null) {
      throw new PassportClientException("Invalid error response type");
    } else if (response.exception != null) {
      throw new PassportClientException(response.exception);
    } else {
      throw new PassportClientException("ClientResponse didn't contain a error response or an exception - strange");
    }
  }
}
