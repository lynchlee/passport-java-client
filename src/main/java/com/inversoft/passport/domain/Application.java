/*
 * Copyright (c) 2015, Inversoft Inc., All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.inversoft.passport.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.inversoft.json.ToString;
import com.inversoft.passport.domain.oauth2.OAuth2Configuration;
import static com.inversoft.passport.domain.util.Normalizer.trim;

/**
 * @author Seth Musselman
 */
public class Application implements Buildable<Application> {
  public static final UUID PASSPORT_ID = UUID.fromString("3C219E58-ED0E-4B18-AD48-F4F92793AE32");

  public boolean active;

  public CleanSpeakConfiguration cleanSpeakConfiguration;

  public UUID id;

  public String name;

  public OAuth2Configuration oauthConfiguration = new OAuth2Configuration();

  public List<ApplicationRole> roles = new ArrayList<>();

  public Application() {
  }

  public Application(String name) {
    this.name = name;
  }

  public Application(UUID id, String name, boolean active, CleanSpeakConfiguration cleanSpeakConfiguration,
                     ApplicationRole... roles) {
    this.id = id;
    this.name = name;
    this.active = active;
    this.cleanSpeakConfiguration = cleanSpeakConfiguration;
    Collections.addAll(this.roles, roles);
  }

  public Application(UUID id, String name, boolean active, CleanSpeakConfiguration cleanSpeakConfiguration,
                     OAuth2Configuration oAuthConfiguration, ApplicationRole... roles) {
    this.id = id;
    this.name = name;
    this.active = active;
    this.cleanSpeakConfiguration = cleanSpeakConfiguration;
    this.oauthConfiguration = oAuthConfiguration;
    Collections.addAll(this.roles, roles);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Application)) {
      return false;
    }
    Application that = (Application) o;
    return Objects.equals(active, that.active) &&
        Objects.equals(cleanSpeakConfiguration, that.cleanSpeakConfiguration) &&
        Objects.equals(oauthConfiguration, that.oauthConfiguration) &&
        Objects.equals(name, that.name) &&
        Objects.equals(roles, that.roles);
  }

  public ApplicationRole getRole(String name) {
    for (ApplicationRole role : roles) {
      if (role.name.equals(name)) {
        return role;
      }
    }

    return null;
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, name, cleanSpeakConfiguration, oauthConfiguration, roles);
  }

  public void normalize() {
    name = trim(name);

    if (cleanSpeakConfiguration != null) {
      cleanSpeakConfiguration.normalize();
    }

    if (oauthConfiguration != null) {
      oauthConfiguration.normalize();
    }

    roles.forEach(ApplicationRole::normalize);
  }

  public Application secure() {
    if (oauthConfiguration != null) {
      oauthConfiguration.clientSecret = null;
    }
    return this;
  }

  public Application sortRoles() {
    roles.sort(ApplicationRole::compareTo);
    return this;
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
