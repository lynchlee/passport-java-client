[#import "_macros.ftl" as global/]
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
package com.inversoft.passport.client;

import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.inversoft.error.Errors;
import com.inversoft.json.JacksonModule;
import com.inversoft.passport.domain.api.ApplicationRequest;
import com.inversoft.passport.domain.api.ApplicationResponse;
import com.inversoft.passport.domain.api.AuditLogRequest;
import com.inversoft.passport.domain.api.AuditLogResponse;
import com.inversoft.passport.domain.api.EmailTemplateRequest;
import com.inversoft.passport.domain.api.EmailTemplateResponse;
import com.inversoft.passport.domain.api.LoginRequest;
import com.inversoft.passport.domain.api.LoginResponse;
import com.inversoft.passport.domain.api.PreviewRequest;
import com.inversoft.passport.domain.api.PreviewResponse;
import com.inversoft.passport.domain.api.PublicKeyResponse;
import com.inversoft.passport.domain.api.SystemConfigurationRequest;
import com.inversoft.passport.domain.api.SystemConfigurationResponse;
import com.inversoft.passport.domain.api.TwoFactorRequest;
import com.inversoft.passport.domain.api.UserActionReasonRequest;
import com.inversoft.passport.domain.api.UserActionReasonResponse;
import com.inversoft.passport.domain.api.UserActionRequest;
import com.inversoft.passport.domain.api.UserActionResponse;
import com.inversoft.passport.domain.api.UserCommentRequest;
import com.inversoft.passport.domain.api.UserCommentResponse;
import com.inversoft.passport.domain.api.UserRequest;
import com.inversoft.passport.domain.api.UserResponse;
import com.inversoft.passport.domain.api.WebhookRequest;
import com.inversoft.passport.domain.api.WebhookResponse;
import com.inversoft.passport.domain.api.email.SendRequest;
import com.inversoft.passport.domain.api.email.SendResponse;
import com.inversoft.passport.domain.api.jwt.IssueResponse;
import com.inversoft.passport.domain.api.jwt.RefreshRequest;
import com.inversoft.passport.domain.api.jwt.RefreshResponse;
import com.inversoft.passport.domain.api.jwt.ValidateResponse;
import com.inversoft.passport.domain.api.report.DailyActiveUserReportResponse;
import com.inversoft.passport.domain.api.report.LoginReportResponse;
import com.inversoft.passport.domain.api.report.MonthlyActiveUserReportResponse;
import com.inversoft.passport.domain.api.report.RegistrationReportResponse;
import com.inversoft.passport.domain.api.report.TotalsReportResponse;
import com.inversoft.passport.domain.api.report.UserLoginReportResponse;
import com.inversoft.passport.domain.api.user.ActionRequest;
import com.inversoft.passport.domain.api.user.ActionResponse;
import com.inversoft.passport.domain.api.user.ChangePasswordRequest;
import com.inversoft.passport.domain.api.user.ForgotPasswordRequest;
import com.inversoft.passport.domain.api.user.ForgotPasswordResponse;
import com.inversoft.passport.domain.api.user.ImportRequest;
import com.inversoft.passport.domain.api.user.RegistrationRequest;
import com.inversoft.passport.domain.api.user.RegistrationResponse;
import com.inversoft.passport.domain.api.user.SearchResponse;
import com.inversoft.passport.domain.search.AuditLogSearchCriteria;
import com.inversoft.passport.domain.search.SortField;
import com.inversoft.passport.domain.search.UserSearchCriteria;
import com.inversoft.rest.ClientResponse;
import com.inversoft.rest.JSONBodyHandler;
import com.inversoft.rest.JSONResponseHandler;
import com.inversoft.rest.RESTClient;

/**
 * Client that connects to a Passport server and provides access to the full set of Passport APIs.
 * <p/>
 * When any method is called the return value is always a ClientResponse object. When an API call was successful, the
 * response will contain the response from the server. This might be empty or contain an success object or an error
 * object. If there was a validation error or any other type of error, this will return the Errors object in the
 * response. Additionally, if Passport could not be contacted because it is down or experiencing a failure, the response
 * will contain an Exception, which could be an IOException.
 *
 * @author Brian Pontarelli
 */
@SuppressWarnings("unused")
public class PassportClient {
  public static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                                                                    .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                                                                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                                                                    .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                                                                    .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
                                                                    .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                                                                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                                                    .registerModule(new JacksonModule());

  private final String apiKey;

  private final String baseURL;

  public int connectTimeout = 2000;

  public int readTimeout = 2000;

  public PassportClient(String apiKey, String baseURL) {
    this.apiKey = apiKey;
    this.baseURL = baseURL;
  }

[#list apis as api]
  /**
  [#list api.comments as comment]
   * ${comment}
  [/#list]
   *
  [#list api.params![] as param]
    [#if !param.constant??]
   * @param ${param.name} ${param.comments?join("\n  *     ")}
    [/#if]
  [/#list]
   * @return The ClientResponse object.
   */
  public ClientResponse<${api.successResponse}, ${api.errorResponse}> ${api.methodName}(${global.methodParameters(api, "java")}) {
    return start(${api.successResponse}.${(api.successResponse == 'Void')?then('TYPE', 'class')}, ${api.errorResponse}.${(api.errorResponse == 'Void')?then('TYPE', 'class')}).uri("${api.uri}")
                        [#if api.authorization??]
                            .authorization(${api.authorization})
                        [/#if]
                        [#list api.params![] as param]
                          [#if param.type == "urlSegment"]
                            .urlSegment(${(param.constant?? && param.constant)?then(param.value, param.name)})
                          [#elseif param.type == "urlParameter"]
                            .urlParameter("${param.parameterName}", ${(param.constant?? && param.constant)?then(param.value, param.name)})
                          [#elseif param.type == "body"]
                            .bodyHandler(new JSONBodyHandler(${param.name}, objectMapper))
                          [/#if]
                        [/#list]
                            .${api.method}()
                            .go();
  }

[/#list]
  /**
   * Searches the audit logs with the specified criteria and pagination.
   *
   * @param search The search criteria and pagination information.
   * @return The ClientResponse object.
   */
  public ClientResponse<AuditLogResponse, Void> searchAuditLogs(AuditLogSearchCriteria search) {
    return start(AuditLogResponse.class, Void.TYPE).uri("/api/system/audit-log")
                            .urlParameter("search.user", search.user)
                            .urlParameter("search.message", search.message)
                            .urlParameter("search.end", search.end)
                            .urlParameter("search.start", search.start)
                            .urlParameter("search.orderBy", search.orderBy)
                            .urlParameter("search.startRow", search.startRow)
                            .urlParameter("search.numberOfResults", search.numberOfResults)
                            .get()
                            .go();
  }

  /**
   * Retrieves the users for the given search criteria and pagination.
   *
   * @param search The search criteria and pagination constraints. Fields used: queryString, numberOfResults, startRow,
   *               and sort fields.
   * @return When successful, the response will contain the users that match the search criteria and pagination
   * constraints. If there was a validation error or any other type of error, this will return the Errors object in the
   * response. Additionally, if Passport could not be contacted because it is down or experiencing a failure, the
   * response will contain an Exception, which could be an IOException.
   */
  public ClientResponse<UserResponse, Errors> searchUsersByQueryString(UserSearchCriteria search) {
    RESTClient<UserResponse, Errors> client = start(UserResponse.class, Errors.class).uri("/api/user/search")
                                                                                     .urlParameter("queryString", search.queryString)
                                                                                     .urlParameter("numberOfResults", search.numberOfResults)
                                                                                     .urlParameter("startRow", search.startRow)
                                                                                     .get();

    if (search.sortFields != null) {
      for (int i = 0; i < search.sortFields.size(); i++) {
        SortField field = search.sortFields.get(i);
        client.urlParameter("sortFields[" + i + "].name", field.name)
              .urlParameter("sortFields[" + i + "].missing", field.missing)
              .urlParameter("sortFields[" + i + "].order", field.order);
      }
    }

    return client.go();
  }

  private <T, U> RESTClient<T, U> start(Class<T> type, Class<U> errorType) {
    return new RESTClient<>(type, errorType).authorization(apiKey)
                                               .successResponseHandler(type != Void.TYPE ? new JSONResponseHandler<>(type, objectMapper) : null)
                                               .errorResponseHandler(errorType != Void.TYPE ? new JSONResponseHandler<>(errorType, objectMapper) : null)
                                               .url(baseURL)
                                               .connectTimeout(connectTimeout)
                                               .readTimeout(readTimeout);
  }
}