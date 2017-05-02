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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.inversoft.passport.domain.util.Normalizer;

/**
 * Storage for additional data about the user.
 *
 * @author Brian Pontarelli
 */
public class UserData implements Buildable<UserData> {
  public final Map<String, Object> attributes = new LinkedHashMap<>();

  public final List<Locale> preferredLanguages = new ArrayList<>();

  public UserData() {
  }

  public UserData(Map<String, Object> attributes, Collection<Locale> preferredLanguages) {
    if (attributes != null) {
      this.attributes.putAll(attributes);
    }
    if (preferredLanguages != null) {
      this.preferredLanguages.addAll(preferredLanguages);
    }
  }

  public UserData(UserData data) {
    this.attributes.putAll(data.attributes);
    this.preferredLanguages.addAll(data.preferredLanguages);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserData)) {
      return false;
    }
    UserData userData = (UserData) o;
    return Objects.equals(attributes, userData.attributes) &&
        Objects.equals(preferredLanguages, userData.preferredLanguages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributes, preferredLanguages);
  }

  public void normalize() {
    Normalizer.removeEmpty(attributes);
    preferredLanguages.removeIf(Objects::isNull);
  }
}
