/*
 * Copyright (c) 2016-2017, Inversoft Inc., All Rights Reserved
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
package com.inversoft.passport.domain.search;


import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class SortField {

  /**
   * Sets the value when a field is missing. Can also be set to <code>_last</code> or
   * <code>_first</code> to sort missing last or first respectively.
   */
  public String missing = "_last";

  /**
   * Field name.
   */
  public String name;

  /**
   * Sort ascending or descending;
   */
  public Sort order = Sort.asc;

  public SortField() {
  }

  public SortField(String name) {
    this.name = name;
  }

  public SortField(String name, Sort order, String missing) {
    this.name = name;
    this.order = order;
    this.missing = missing;
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
