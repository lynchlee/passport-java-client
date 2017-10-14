/*
 * Copyright (c) 2015-2017, Inversoft Inc., All Rights Reserved
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

import java.net.URI;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inversoft.json.ToString;
import static com.inversoft.passport.domain.util.Normalizer.toLowerCase;
import static com.inversoft.passport.domain.util.Normalizer.trim;

/**
 * The global view of a User. This object contains all global information about the user including birth date, registration information
 * preferred languages, global attributes, etc.
 *
 * @author Seth Musselman
 */
public class User implements Buildable<User> {
  private final List<UUID> childIds = new ArrayList<>();

  private final List<GroupMember> memberships = new ArrayList<>();

  private final List<UserRegistration> registrations = new ArrayList<>();

  public boolean active;

  public LocalDate birthDate;

  public UUID cleanSpeakId;

  public UserData data;

  public String email;

  public String encryptionScheme;

  public ZonedDateTime expiry;

  public Integer factor;

  public String firstName;

  public String fullName;

  public UUID id;

  public URI imageUrl;

  public ZonedDateTime insertInstant;

  public ZonedDateTime lastLoginInstant;

  public String lastName;

  public String middleName;

  public String mobilePhone;

  public UUID parentId;

  public ParentalConsentType parentalConsentType;

  public String password;

  public boolean passwordChangeRequired;

  public ZonedDateTime passwordLastUpdateInstant;

  public String salt;

  public String timezone;

  public TwoFactorDelivery twoFactorDelivery;

  // new field - only way flip this on, is through an 'Enable 2FA' API call, /api/user/2fa/enable - takes a code, must match secret.

  // TODO
  // 1. add 2FAEnabled to db  [DONE]

  // Enable 2FA API - 2FA Code Generator - Application
  //   [POST]
  //     1. Authorize: identity (API Key, JWT)
  //     2. Return a secret to generate a QRCode, etc?

  //    User takes photo of QR Code to sync their App with the 2FA secret
  //       - Now they get a code from their app.

  //    User Calls API :
  //       1. Authorize: identity (API Key, JWT or Verification Id)
  //       2. Send in Code

  //      [200] OK. User is updated, set to twFactorEnabled = true
  //      [400] Nope, you suck.
  //


  // Enable 2FA API - Push - SMS
  //   [POST]
  //     1. Authorize: identity (API Key, JWT)
  //     2. Send mobile number - or omit if the user already has mobile configured, noop if it has it.
  //     3. Backend sends push after saving new secret to user, twoFactorEnabled = false, twoFactorDelivery = Text
  //
  //        [200] OK, wait for SMS to mobile.

  //    User receives push, now they have a code.

  //    User Calls API :
  //       1. Authorize: identity (API Key, JWT or Verification Id)
  //       2. Send in Code

  //      [200] OK. User is updated, set to twFactorEnabled = true
  //      [400] Nope, you suck.
  //
  //
  //

public boolean twoFactorEnabled;
  public String twoFactorSecret;

  public String username;

  public ContentStatus usernameStatus;

  public String verificationId;

  public ZonedDateTime verificationIdCreateInstant;

  public boolean verified;

  public User() {
  }

  public User(UUID id, String email, String username, String password, String salt, LocalDate birthDate,
              String fullName, String firstName, String middleName, String lastName, String encryptionScheme,
              ZonedDateTime expiry, boolean active, String timezone, UUID cleanSpeakId, UserData data, boolean verified,
              String verificationId, ContentStatus usernameStatus, String twoFactorSecret,
              URI imageUrl, UserRegistration... registrations) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.salt = salt;
    this.birthDate = birthDate;
    this.encryptionScheme = encryptionScheme;
    this.expiry = expiry;
    this.active = active;
    this.username = username;
    this.timezone = timezone;
    this.fullName = fullName;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.data = data;
    this.cleanSpeakId = cleanSpeakId;
    this.verificationId = verificationId;
    this.verified = verified;
    this.usernameStatus = usernameStatus;
    this.twoFactorSecret = twoFactorSecret;
    this.imageUrl = imageUrl;
    Collections.addAll(this.registrations, registrations);
    normalize();
  }

  public User(User user) {
    this.active = user.active;
    this.birthDate = user.birthDate;
    this.childIds.addAll(user.childIds);
    this.cleanSpeakId = user.cleanSpeakId;
    this.email = user.email;
    this.encryptionScheme = user.encryptionScheme;
    this.expiry = user.expiry;
    this.factor = user.factor;
    this.firstName = user.firstName;
    this.fullName = user.fullName;
    this.id = user.id;
    this.imageUrl = user.imageUrl;
    this.insertInstant = user.insertInstant;
    this.lastLoginInstant = user.lastLoginInstant;
    this.lastName = user.lastName;
    this.memberships.addAll(user.memberships.stream().map(GroupMember::new).collect(Collectors.toList()));
    this.middleName = user.middleName;
    this.mobilePhone = user.mobilePhone;
    this.parentalConsentType = user.parentalConsentType;
    this.parentId = user.parentId;
    this.password = user.password;
    this.passwordChangeRequired = user.passwordChangeRequired;
    this.passwordLastUpdateInstant = user.passwordLastUpdateInstant;
    this.registrations.addAll(user.registrations.stream().map(UserRegistration::new).collect(Collectors.toList()));
    this.salt = user.salt;
    this.timezone = user.timezone;
    this.twoFactorEnabled = user.twoFactorEnabled;
    this.twoFactorSecret = user.twoFactorSecret;
    this.username = user.username;
    this.usernameStatus = user.usernameStatus;
    this.verificationId = user.verificationId;
    this.verificationIdCreateInstant = user.verificationIdCreateInstant;
    this.verified = user.verified;

    if (user.data != null) {
      this.data = new UserData(user.data);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    sort();
    user.sort();
    return Objects.equals(active, user.active) &&
        Objects.equals(birthDate, user.birthDate) &&
        Objects.equals(childIds, user.childIds) &&
        Objects.equals(cleanSpeakId, user.cleanSpeakId) &&
        Objects.equals(parentalConsentType, user.parentalConsentType) &&
        Objects.equals(data, user.data) &&
        Objects.equals(encryptionScheme, user.encryptionScheme) &&
        Objects.equals(email, user.email) &&
        Objects.equals(expiry, user.expiry) &&
        Objects.equals(factor, user.factor) &&
        Objects.equals(firstName, user.firstName) &&
        Objects.equals(fullName, user.fullName) &&
        Objects.equals(imageUrl, user.imageUrl) &&
        Objects.equals(insertInstant, user.insertInstant) &&
        Objects.equals(lastLoginInstant, user.lastLoginInstant) &&
        Objects.equals(lastName, user.lastName) &&
        Objects.equals(memberships, user.memberships) &&
        Objects.equals(middleName, user.middleName) &&
        Objects.equals(mobilePhone, user.mobilePhone) &&
        Objects.equals(parentId, user.parentId) &&
        Objects.equals(password, user.password) &&
        Objects.equals(passwordChangeRequired, user.passwordChangeRequired) &&
        Objects.equals(passwordLastUpdateInstant, user.passwordLastUpdateInstant) &&
        Objects.equals(registrations, user.registrations) &&
        Objects.equals(salt, user.salt) &&
        Objects.equals(timezone, user.timezone) &&
        Objects.equals(twoFactorDelivery, user.twoFactorDelivery) &&
        Objects.equals(twoFactorSecret, user.twoFactorSecret) &&
        Objects.equals(username, user.username) &&
        Objects.equals(usernameStatus, user.usernameStatus) &&
        Objects.equals(verificationId, user.verificationId) &&
        Objects.equals(verificationIdCreateInstant, user.verificationIdCreateInstant) &&
        Objects.equals(verified, user.verified);
  }

  @JsonIgnore
  public int getAge() {
    if (birthDate == null) {
      return -1;
    }

    return (int) birthDate.until(LocalDate.now(), ChronoUnit.YEARS);
  }

  public List<UUID> getChildIds() {
    return childIds;
  }

  public UserData getDataForApplication(UUID id) {
    return getRegistrations().stream()
                             .filter((reg) -> reg.applicationId.equals(id))
                             .findFirst()
                             .map(userRegistration -> userRegistration.data)
                             .orElse(null);
  }

  /**
   * @return return a single identity value preferring email over username.
   */
  @JsonIgnore
  public String getLogin() {
    return email == null ? username : email;
  }

  public List<GroupMember> getMemberships() {
    return memberships;
  }

  @JsonIgnore
  public String getName() {
    if (fullName != null) {
      return fullName;
    }
    if (firstName != null) {
      return firstName + (lastName != null ? " " + lastName : "");
    }

    return null;
  }

  @JsonIgnore
  public List<Locale> getPreferredLanguages() {
    return data == null ? Collections.emptyList() : data.preferredLanguages;
  }

  public UserRegistration getRegistrationForApplication(UUID id) {
    return getRegistrations().stream()
                             .filter(reg -> reg.applicationId.equals(id))
                             .findFirst()
                             .orElse(null);
  }

  public List<UserRegistration> getRegistrations() {
    return registrations;
  }

  public Set<String> getRoleNamesForApplication(UUID id) {
    UserRegistration registration = getRegistrationForApplication(id);
    return registration != null ? registration.roles : null;
  }

  /**
   * Return true if user data is provided for this user or any registrations.
   *
   * @return true if user data exists.
   */
  public boolean hasUserData() {
    if (data != null && !data.attributes.isEmpty()) {
      return true;
    }

    for (UserRegistration userRegistration : registrations) {
      if (userRegistration.data != null && !userRegistration.data.attributes.isEmpty()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, birthDate, childIds, cleanSpeakId, parentalConsentType, data, email, encryptionScheme, expiry,
                        factor, firstName, fullName, imageUrl, insertInstant, lastLoginInstant, lastName, memberships, middleName, mobilePhone, parentId, password,
                        passwordChangeRequired, passwordLastUpdateInstant, registrations, salt, timezone, twoFactorDelivery, twoFactorSecret, username,
                        usernameStatus, verificationId, verificationIdCreateInstant, verified);
  }

  /**
   * Attempt to retrieve the users email address first by checking the top level and then in user data.
   *
   * @return an email address or null if no email address is found.
   */
  public String lookupEmail() {
    if (email != null) {
      return email;
    } else if (data != null && data.attributes.containsKey("email")) {
      return data.attributes.get("email").toString();
    }
    return null;
  }

  /**
   * Normalizes all of the fields.
   */
  public void normalize() {
    email = toLowerCase(trim(email));
    if (data != null) {
      data.normalize();
    }
    encryptionScheme = trim(encryptionScheme);
    firstName = trim(firstName);
    fullName = trim(fullName);
    lastName = trim(lastName);
    middleName = trim(middleName);
    mobilePhone = trim(mobilePhone);
    timezone = trim(timezone);
    username = trim(username);
    getRegistrations().forEach(UserRegistration::normalize);
  }

  /**
   * Clear out sensitive data. Password, salt, etc.
   */
  public User secure() {
    salt = null;
    password = null;
    factor = null;
    encryptionScheme = null;
    twoFactorSecret = null;
    return this;
  }

  public User sort() {
    this.childIds.sort(UUID::compareTo);
    this.registrations.sort(Comparator.comparing(ur -> ur.applicationId));
    return this;
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
