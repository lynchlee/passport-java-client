/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.client;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import com.inversoft.rest.ClientResponse;

/**
 * This is a helper delegate that makes it simpler to invoke the {@link PassportClient} methods and automatically deals
 * with the {@link ClientResponse} object for you.
 *
 * @author Brian Pontarelli
 */
public class LambdaDelegate {
  public final PassportClient client;

  public final Consumer<ClientResponse<?, ?>> errorConsumer;

  public final Function<ClientResponse<?, ?>, ?> successFunction;

  public LambdaDelegate(PassportClient client, Function<ClientResponse<?, ?>, ?> successFunction,
                        Consumer<ClientResponse<?, ?>> errorConsumer) {
    Objects.requireNonNull(client, "You can't use the lambda delegate unless you supply a PassportClient");
    Objects.requireNonNull(successFunction, "You can't use the lambda delegate unless you supply a success Function and error Consumer");
    Objects.requireNonNull(errorConsumer, "You can't use the lambda delegate unless you supply a success Function and error Consumer");
    this.client = client;
    this.errorConsumer = errorConsumer;
    this.successFunction = successFunction;
  }

  /**
   * Executes the delegated PassportClient call using the given function (to make the API call) and the function and
   * consumer passed into the constructor.
   *
   * @param function The Function that should invoke the PassportClient method.
   * @param <T>      The success response type.
   * @param <U>      The error response type.
   * @return The success response type if the API call was successful.
   */
  @SuppressWarnings("unchecked")
  public <T, U> T execute(Function<PassportClient, ClientResponse<T, U>> function) {
    ClientResponse<T, U> response = function.apply(this.client);
    if (response.wasSuccessful()) {
      return (T) successFunction.apply(response);
    } else {
      errorConsumer.accept(response);
    }

    return null;
  }
}
