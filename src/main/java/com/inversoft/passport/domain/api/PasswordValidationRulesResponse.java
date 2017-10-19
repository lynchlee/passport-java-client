/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.api;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.passport.domain.PasswordValidationRules;

/**
 * @author Daniel DeGroff
 */
public class PasswordValidationRulesResponse {
  public PasswordValidationRules passwordValidationRules;

  public PasswordValidationRulesResponse(PasswordValidationRules passwordValidationRules) {
    this.passwordValidationRules = passwordValidationRules;
  }

  @JacksonConstructor
  public PasswordValidationRulesResponse() {
  }
}
