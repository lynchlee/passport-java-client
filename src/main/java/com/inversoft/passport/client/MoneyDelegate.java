/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.client;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.inversoft.rest.ClientResponse;

/**
 * @author Brian Pontarelli
 */
public class MoneyDelegate {
  public final Consumer<ClientResponse<?, ?>> errorConsumer;

  public final Function<ClientResponse<?, ?>, ?> successFunction;

  public MoneyDelegate(Consumer<ClientResponse<?, ?>> errorConsumer,
                       Function<ClientResponse<?, ?>, ?> successFunction) {
    this.errorConsumer = errorConsumer;
    this.successFunction = successFunction;
  }

  public <T,U> T delegate(Supplier<ClientResponse<T, U>> supplier) {
    Objects.requireNonNull(successFunction, "You can't use the money-methods unless you supply a success Function and error Consumer");
    Objects.requireNonNull(errorConsumer, "You can't use the money-methods unless you supply a success Function and error Consumer");

    ClientResponse<T, U> response = supplier.get();
    if (response.wasSuccessful()) {
      return (T) successFunction.apply(response);
    } else {
      errorConsumer.accept(response);
    }

    return null;
  }
}
