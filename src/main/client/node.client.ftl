[#import "_macros.ftl" as global/]
/*/*
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

'use strict';

const RESTClient = require('./RESTClient.js');
require('promise');

const PassportClient = function(apiKey, host) {
  this.apiKey = apiKey;
  this.host = host;
};

PassportClient.constructor = PassportClient;
//noinspection JSUnusedGlobalSymbols
PassportClient.prototype = {

[#list apis as api]
  /**
  [#list api.comments as comment]
   * ${comment}
  [/#list]
   *
  [#list api.params![] as param]
    [#if !param.constant??]
   * @param {${global.convertType(param.javaType, "js")}} ${param.name} ${param.comments?join("\n   *    ")}
    [/#if]
  [/#list]
   * @return {Promise} A Promise for the Passport call.
   */
  ${api.methodName}: function(${global.methodParameters(api, "js")}) {
    return new Promise((resolve, reject) => {
      this._start()
          .uri('${api.uri}')
      [#if api.authorization??]
          .authorization(${api.authorization})
      [/#if]
      [#list api.params![] as param]
        [#if param.type == "urlSegment"]
          .urlSegment(${(param.constant?? && param.constant)?then(param.value, param.name)})
        [#elseif param.type == "urlParameter"]
          .urlParameter('${param.parameterName}', ${(param.constant?? && param.constant)?then(param.value, param.name)})
        [#elseif param.type == "body"]
          .setJSONBody(${param.name})
        [/#if]
      [/#list]
          .${api.method}()
          .go(this._responseHandler(resolve, reject));
  },

[/#list]
  /**
   * Searches the audit logs with the specified criteria and pagination.
   *
   * @param {Object} search The search criteria and pagination information.
   * @returns {Promise} A Promise for the Passport call.
   */
  searchAuditLogs: function(search) {
    const client = this._start()
        .uri('/api/system/audit-log')
        .urlParameter("search.user", search.user)
        .urlParameter("search.message", search.message)
        .urlParameter("search.end", search.end)
        .urlParameter("search.start", search.start)
        .urlParameter("search.orderBy", search.orderBy)
        .urlParameter("search.startRow", search.startRow)
        .urlParameter("search.numberOfResults", search.numberOfResults)
        .get();

    return new Promise((resolve, reject) => {
      client.go(this._responseHandler(resolve, reject));
    });
  },

  /**
   * Retrieves the users for the given search criteria and pagination.
   *
   * @param {Object} search The search criteria and pagination constraints. Fields used: queryString, numberOfResults, startRow,
   *               and sort fields.
   * @returns {Promise} A Promise for the Passport call.
   */
  searchUsersByQueryString: function(search) {
    //noinspection JSUnresolvedVariable
    const client = this._start()
        .uri('/api/user/search')
        .urlParameter('queryString', search.queryString)
        .urlParameter("numberOfResults", search.numberOfResults)
        .urlParameter("startRow", search.startRow)
        .get();

    //noinspection JSUnresolvedVariable
    if (search.sortFields !== null && typeof search.sortFields !== 'undefined') {
      //noinspection JSUnresolvedVariable
      for (let i = 0; i < search.sortFields.length; i++) {
        //noinspection JSUnresolvedVariable
        client.urlParameter("sortFields[" + i + "].name", search.sortFields[i].name)
            .urlParameter("sortFields[" + i + "].missing", search.sortFields[i].missing)
            .urlParameter("sortFields[" + i + "].order", search.sortFields[i].order);
      }
    }

    return new Promise((resolve, reject) => {
      client.go(this._responseHandler(resolve, reject));
    });
  },

  /* ===================================================================================================================
   * Private methods
   * ===================================================================================================================*/

  /**
   * Require a parameter to be defined, if null or un-defined this throws an exception.
   * @param {Object} value The value that must be defined.
   * @param {string} name The name of the parameter.
   * @private
   */
  _requireNonNull: function(value, name) {
    if (typeof value === 'undefined' || value === null) {
      throw new Error(name + ' parameter is required.');
    }
  },

  /**
   * Returns a function to handle the promises for each call.
   *
   * @param resolve The promise's resolve function.
   * @param reject The promise's reject function.
   * @returns {Function} The function that will call either the resolve or reject functions based on the ClientResponse.
   * @private
   */
  _responseHandler: function(resolve, reject) {
    return function(response) {
      if (response.wasSuccessful()) {
        resolve(response);
      } else {
        reject(response);
      }
    };
  },

  /**
   * creates a rest client
   *
   * @returns {RESTClient} The RESTClient that will be used to call.
   * @private
   */
  _start: function() {
    return new RESTClient().authorization(this.apiKey).setUrl(this.host);
  }
};

module.exports = PassportClient;