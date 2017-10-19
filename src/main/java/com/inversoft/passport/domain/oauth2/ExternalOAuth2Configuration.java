/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 */
package com.inversoft.passport.domain.oauth2;

import java.net.URI;

import com.inversoft.passport.domain.Buildable;

/**
 * @author Daniel DeGroff
 */
public class ExternalOAuth2Configuration implements Buildable<ExternalOAuth2Configuration> {
  public OAuth2Configuration application = new OAuth2Configuration();

  public GlobalOAuth2Configuration global = new GlobalOAuth2Configuration();

  public static class GlobalOAuth2Configuration implements Buildable<GlobalOAuth2Configuration> {
    public int httpSessionMaxInactiveInterval;

    public URI logoutURL;
  }
}
