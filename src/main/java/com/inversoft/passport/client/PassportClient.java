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
import com.inversoft.passport.domain.api.AuditLogSearchRequest;
import com.inversoft.passport.domain.api.AuditLogSearchResponse;
import com.inversoft.passport.domain.api.EmailTemplateRequest;
import com.inversoft.passport.domain.api.EmailTemplateResponse;
import com.inversoft.passport.domain.api.IntegrationRequest;
import com.inversoft.passport.domain.api.IntegrationResponse;
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
import com.inversoft.passport.domain.api.user.SearchRequest;
import com.inversoft.passport.domain.api.user.SearchResponse;
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

  /**
   * Takes an action on a user. The user being actioned is called the "actionee" and the user taking the action is called the
   * "actioner". Both user ids are required. You pass the actionee's user id into the method and the actioner's is put into the
   * request object.
   *
   * @param actioneeUserId The actionee's user id.
   * @param request The action request that includes all of the information about the action being taken including
  *     the id of the action, any options and the duration (if applicable).
   * @return The ClientResponse object.
   */
  public ClientResponse<ActionResponse, Errors> actionUser(UUID actioneeUserId, ActionRequest request) {
    return start(ActionResponse.class, Errors.class).uri("/api/user/action")
                            .urlSegment(actioneeUserId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Cancels the user action.
   *
   * @param actionId The action id of the action to cancel.
   * @param request The action request that contains the information about the cancellation.
   * @return The ClientResponse object.
   */
  public ClientResponse<ActionResponse, Errors> cancelAction(UUID actionId, ActionRequest request) {
    return start(ActionResponse.class, Errors.class).uri("/api/user/action")
                            .urlSegment(actionId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .delete()
                            .go();
  }

  /**
   * Changes a user's password using the verification id. This usually occurs after an email has been sent to the user
   * and they clicked on a link to reset their password.
   *
   * @param verificationId The verification id used to find the user.
   * @param request The change password request that contains all of the information used to change the password.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> changePassword(String verificationId, ChangePasswordRequest request) {
    return start(Void.TYPE, Errors.class).uri("/api/user/change-password")
                            .urlSegment(verificationId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Changes a user's password using their identity (login id and password). Using a loginId instead of the verificationId
   * bypasses the email verification and allows a password to be changed directly without first calling the #forgotPassword
   * method.
   *
   * @param request The change password request that contains all of the information used to change the password.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> changePasswordByIdentity(ChangePasswordRequest request) {
    return start(Void.TYPE, Errors.class).uri("/api/user/change-password")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Adds a comment to the user's account.
   *
   * @param request The comment request that contains all of the information used to add the comment to the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> commentOnUser(UserCommentRequest request) {
    return start(Void.TYPE, Errors.class).uri("/api/user/comment")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates an application. You can optionally specify an id for the application, but this is not required.
   *
   * @param applicationId (Optional) The id to use for the application.
   * @param request The application request that contains all of the information used to create the application.
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Errors> createApplication(UUID applicationId, ApplicationRequest request) {
    return start(ApplicationResponse.class, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates a new role for an application. You must specify the id of the application you are creating the role for.
   * You can optionally specify an id for the role inside the ApplicationRole object itself, but this is not required.
   *
   * @param applicationId The id of the application to create the role on.
   * @param roleId (Optional) The id of the role. Defaults to a secure UUID.
   * @param request The application request that contains all of the information used to create the role.
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Errors> createApplicationRole(UUID applicationId, UUID roleId, ApplicationRequest request) {
    return start(ApplicationResponse.class, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .urlSegment("role")
                            .urlSegment(roleId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates an audit log with the message and user name (usually an email). Audit logs should be written anytime you
   * make changes to the Passport database. When using the Passport Backend web interface, any changes are automatically
   * written to the audit log. However, if you are accessing the API, you must write the audit logs yourself.
   *
   * @param request The audit log request that contains all of the information used to create the audit log entry.
   * @return The ClientResponse object.
   */
  public ClientResponse<AuditLogResponse, Errors> createAuditLog(AuditLogRequest request) {
    return start(AuditLogResponse.class, Errors.class).uri("/api/system/audit-log")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates an email template. You can optionally specify an id for the email template when calling this method, but it
   * is not required.
   *
   * @param emailTemplateId (Optional) The id for the template.
   * @param request The email template request that contains all of the information used to create the email template.
   * @return The ClientResponse object.
   */
  public ClientResponse<EmailTemplateResponse, Errors> createEmailTemplate(UUID emailTemplateId, EmailTemplateRequest request) {
    return start(EmailTemplateResponse.class, Errors.class).uri("/api/email/template")
                            .urlSegment(emailTemplateId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates a user with an optional id.
   *
   * @param userId (Optional) The id for the user.
   * @param request The user request that contains all of the information used to create the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserResponse, Errors> createUser(UUID userId, UserRequest request) {
    return start(UserResponse.class, Errors.class).uri("/api/user")
                            .urlSegment(userId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates a user action. This action cannot be taken on a user until this call successfully returns. Anytime after
   * that the user action can be applied to any user.
   *
   * @param userActionId (Optional) The id for the user action.
   * @param request The user action request that contains all of the information used to create the user action.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionResponse, Errors> createUserAction(UUID userActionId, UserActionRequest request) {
    return start(UserActionResponse.class, Errors.class).uri("/api/user-action")
                            .urlSegment(userActionId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates a user reason. This user action reason cannot be used when actioning a user until this call completes
   * successfully. Anytime after that the user action reason can be used.
   *
   * @param userActionReasonId (Optional) The id for the user action reason.
   * @param request The user action reason request that contains all of the information used to create the user action reason.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionReasonResponse, Errors> createUserActionReason(UUID userActionReasonId, UserActionReasonRequest request) {
    return start(UserActionReasonResponse.class, Errors.class).uri("/api/user-action-reason")
                            .urlSegment(userActionReasonId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Creates a webhook. You can optionally specify an id for the webhook when calling this method, but it is not required.
   *
   * @param webhookId (Optional) The id for the webhook.
   * @param request The webhook request that contains all of the information used to create the webhook.
   * @return The ClientResponse object.
   */
  public ClientResponse<WebhookResponse, Errors> createWebhook(UUID webhookId, WebhookRequest request) {
    return start(WebhookResponse.class, Errors.class).uri("/api/webhook")
                            .urlSegment(webhookId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Deactivates the application with the given id.
   *
   * @param applicationId The id of the application to deactivate.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deactivateApplication(UUID applicationId) {
    return start(Void.TYPE, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .delete()
                            .go();
  }

  /**
   * Deactivates the user with the given id.
   *
   * @param userId The id of the user to deactivate.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deactivateUser(UUID userId) {
    return start(Void.TYPE, Errors.class).uri("/api/user")
                            .urlSegment(userId)
                            .delete()
                            .go();
  }

  /**
   * Deactivates the user action with the given id.
   *
   * @param userActionId The id of the user action to deactivate.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deactivateUserAction(UUID userActionId) {
    return start(Void.TYPE, Errors.class).uri("/api/user-action")
                            .urlSegment(userActionId)
                            .delete()
                            .go();
  }

  /**
   * Deactivates the users with the given ids.
   *
   * @param userIds The ids of the users to deactivate.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deactivateUsers(Collection<UUID> userIds) {
    return start(Void.TYPE, Errors.class).uri("/api/user/bulk")
                            .urlParameter("userId", userIds)
                            .delete()
                            .go();
  }

  /**
   * Hard deletes an application. This is a dangerous operation and should not be used in most circumstances. This will
   * delete the application, any registrations for that application, metrics and reports for the application, all the
   * roles for the application, and any other data associated with the application. This operation could take a very
   * long time, depending on the amount of data in your database.
   *
   * @param applicationId The id of the application to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteApplication(UUID applicationId) {
    return start(Void.TYPE, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .urlParameter("hardDelete", true)
                            .delete()
                            .go();
  }

  /**
   * Hard deletes an application role. This is a dangerous operation and should not be used in most circumstances. This
   * permanently removes the given role from all users that had it.
   *
   * @param applicationId The id of the application to deactivate.
   * @param roleId The id of the role to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteApplicationRole(UUID applicationId, UUID roleId) {
    return start(Void.TYPE, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .urlSegment("role")
                            .urlSegment(roleId)
                            .delete()
                            .go();
  }

  /**
   * Deletes the email template for the given id.
   *
   * @param emailTemplateId The id of the email template to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteEmailTemplate(UUID emailTemplateId) {
    return start(Void.TYPE, Errors.class).uri("/api/email/template")
                            .urlSegment(emailTemplateId)
                            .delete()
                            .go();
  }

  /**
   * Deletes the user registration for the given user and application.
   *
   * @param userId The id of the user whose registration is being deleted.
   * @param applicationId The id of the application to remove the registration for.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteRegistration(UUID userId, UUID applicationId) {
    return start(Void.TYPE, Errors.class).uri("/api/user/registration")
                            .urlSegment(userId)
                            .urlSegment(applicationId)
                            .delete()
                            .go();
  }

  /**
   * Deletes the user for the given id. This permanently deletes all information, metrics, reports and data associated
   * with the user.
   *
   * @param userId The id of the user to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteUser(UUID userId) {
    return start(Void.TYPE, Errors.class).uri("/api/user")
                            .urlSegment(userId)
                            .urlParameter("hardDelete", true)
                            .delete()
                            .go();
  }

  /**
   * Deletes the user action for the given id. This permanently deletes the user action and also any history and logs of
   * the action being applied to any users.
   *
   * @param userActionId The id of the user action to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteUserAction(UUID userActionId) {
    return start(Void.TYPE, Errors.class).uri("/api/user-action")
                            .urlSegment(userActionId)
                            .urlParameter("hardDelete", true)
                            .delete()
                            .go();
  }

  /**
   * Deletes the user action reason for the given id.
   *
   * @param userActionReasonId The id of the user action reason to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteUserActionReason(UUID userActionReasonId) {
    return start(Void.TYPE, Errors.class).uri("/api/user-action-reason")
                            .urlSegment(userActionReasonId)
                            .delete()
                            .go();
  }

  /**
   * Deletes the users with the given ids.
   *
   * @param userIds The ids of the users to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteUsers(Collection<UUID> userIds) {
    return start(Void.TYPE, Errors.class).uri("/api/user/bulk")
                            .urlParameter("userId", userIds)
                            .urlParameter("hardDelete", true)
                            .delete()
                            .go();
  }

  /**
   * Deletes the webhook for the given id.
   *
   * @param webhookId The id of the webhook to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> deleteWebhook(UUID webhookId) {
    return start(Void.TYPE, Errors.class).uri("/api/webhook")
                            .urlSegment(webhookId)
                            .delete()
                            .go();
  }

  /**
   * Exchange a refresh token for a new Access Token (JWT).
   *
   * @param request The refresh request.
   * @return The ClientResponse object.
   */
  public ClientResponse<RefreshResponse, Errors> exchangeRefreshTokenForAccessToken(RefreshRequest request) {
    return start(RefreshResponse.class, Errors.class).uri("/api/jwt/refresh")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Begins the forgot password sequence, which kicks off an email to the user so that they can reset their password.
   *
   * @param request The request that contains the information about the user so that they can be emailed.
   * @return The ClientResponse object.
   */
  public ClientResponse<ForgotPasswordResponse, Errors> forgotPassword(ForgotPasswordRequest request) {
    return start(ForgotPasswordResponse.class, Errors.class).uri("/api/user/forgot-password")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Bulk imports multiple users. This does some validation, but then tries to run batch inserts of users. This reduces
   * latency when inserting lots of users. Therefore, the error response might contain some information about failures,
   * but it will likely be pretty generic.
   *
   * @param request The request that contains all of the information about all of the users to import.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> importUsers(ImportRequest request) {
    return start(Void.TYPE, Errors.class).uri("/api/user/import")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Issue a new access token (JWT) for the requested Application after ensuring the provided JWT is valid. A valid
   * access token is properly signed and not expired.
   * <p/>
   * This API may be used in an SSO configuration to issue new tokens for another application after the user has
   * obtained a valid token from authentication.
   *
   * @param applicationId The Application Id for which you are requesting a new access token be issued.
   * @param encodedJWT The encoded JWT (access token).
   * @return The ClientResponse object.
   */
  public ClientResponse<IssueResponse, Errors> issueAccessToken(UUID applicationId, String encodedJWT) {
    return start(IssueResponse.class, Errors.class).uri("/api/jwt/issue")
                            .authorization("JWT " + encodedJWT)
                            .urlParameter("applicationId", applicationId)
                            .get()
                            .go();
  }

  /**
   * Logs a user in.
   *
   * @param request The login request that contains the user credentials used to log them in.
   * @return The ClientResponse object.
   */
  public ClientResponse<LoginResponse, Errors> login(LoginRequest request) {
    return start(LoginResponse.class, Errors.class).uri("/api/login")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Sends a ping to Passport indicating that the user was automatically logged into an application. When using
   * Passport's SSO or your own, you should call this if the user is already logged in centrally, but accesses an
   * application where they no longer have a session. This helps correctly track login counts, times and helps with
   * reporting.
   *
   * @param userId The id of the user that was logged in.
   * @param applicationId The id of the application that they logged into.
   * @param callerIPAddress (Optional) The IP address of the end-user that is logging in. If a null value is provided
  *     the IP address will be that of the client or last proxy that sent the request.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> loginPing(UUID userId, UUID applicationId, String callerIPAddress) {
    return start(Void.TYPE, Errors.class).uri("/api/login")
                            .urlSegment(userId)
                            .urlSegment(applicationId)
                            .urlParameter("ipAddress", callerIPAddress)
                            .put()
                            .go();
  }

  /**
   * The Logout API is intended to be used to remove the refresh token and access token cookies if they exist on the
   * client and revoke the refresh token stored. This API does nothing if the request does not contain an access
   * token or refresh token cookies.
   *
   * @param global (Optional) When this value is set to true all of the refresh tokens issued to the owner of the
  *     provided token will be revoked.
   * @param refreshToken (Optional) The refresh_token as a request parameter instead of coming in via a cookie.
  *     If provided this takes precedence over the cookie.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Void> logout(boolean global, String refreshToken) {
    return start(Void.TYPE, Void.TYPE).uri("/api/logout")
                            .urlParameter("global", global)
                            .urlParameter("refreshToken", refreshToken)
                            .post()
                            .go();
  }

  /**
   * Modifies a temporal user action by changing the expiration of the action and optionally adding a comment to the
   * action.
   *
   * @param actionId The id of the action to modify. This is technically the user action log id.
   * @param request The request that contains all of the information about the modification.
   * @return The ClientResponse object.
   */
  public ClientResponse<ActionResponse, Errors> modifyAction(UUID actionId, ActionRequest request) {
    return start(ActionResponse.class, Errors.class).uri("/api/user/action")
                            .urlSegment(actionId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Reactivates the application with the given id.
   *
   * @param applicationId The id of the application to reactivate.
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Errors> reactivateApplication(UUID applicationId) {
    return start(ApplicationResponse.class, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .urlParameter("reactivate", true)
                            .put()
                            .go();
  }

  /**
   * Reactivates the user with the given id.
   *
   * @param userId The id of the user to reactivate.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserResponse, Errors> reactivateUser(UUID userId) {
    return start(UserResponse.class, Errors.class).uri("/api/user")
                            .urlSegment(userId)
                            .urlParameter("reactivate", true)
                            .put()
                            .go();
  }

  /**
   * Reactivates the user action with the given id.
   *
   * @param userActionId The id of the user action to reactivate.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionResponse, Errors> reactivateUserAction(UUID userActionId) {
    return start(UserActionResponse.class, Errors.class).uri("/api/user-action")
                            .urlSegment(userActionId)
                            .urlParameter("reactivate", true)
                            .put()
                            .go();
  }

  /**
   * Registers a user for an application. If you provide the User and the UserRegistration object on this request, it
   * will create the user as well as register them for the application. This is called a Full Registration. However, if
   * you only provide the UserRegistration object, then the user must already exist and they will be registered for the
   * application. The user id can also be provided and it will either be used to look up an existing user or it will be
   * used for the newly created User.
   *
   * @param userId (Optional) The id of the user being registered for the application and optionally created.
   * @param request The request that optionally contains the User and must contain the UserRegistration.
   * @return The ClientResponse object.
   */
  public ClientResponse<RegistrationResponse, Errors> register(UUID userId, RegistrationRequest request) {
    return start(RegistrationResponse.class, Errors.class).uri("/api/user/registration")
                            .urlSegment(userId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Re-sends the verification email to the user.
   *
   * @param email The email address of the user that needs a new verification email.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Void> resendEmailVerification(String email) {
    return start(Void.TYPE, Void.TYPE).uri("/api/user/verify-email")
                            .urlParameter("email", email)
                            .put()
                            .go();
  }

  /**
   * Retrieves a single action log (the log of a user action that was taken on a user previously) for the given id.
   *
   * @param actionId The id of the action to retrieve.
   * @return The ClientResponse object.
   */
  public ClientResponse<ActionResponse, Errors> retrieveAction(UUID actionId) {
    return start(ActionResponse.class, Errors.class).uri("/api/user/action")
                            .urlSegment(actionId)
                            .get()
                            .go();
  }

  /**
   * Retrieves all of the actions for the user with the given id.
   *
   * @param userId The id of the user to fetch the actions for.
   * @return The ClientResponse object.
   */
  public ClientResponse<ActionResponse, Errors> retrieveActions(UUID userId) {
    return start(ActionResponse.class, Errors.class).uri("/api/user/action")
                            .urlParameter("userId", userId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the application for the given id or all of the applications if the id is null.
   *
   * @param applicationId (Optional) The application id.
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Void> retrieveApplication(UUID applicationId) {
    return start(ApplicationResponse.class, Void.TYPE).uri("/api/application")
                            .urlSegment(applicationId)
                            .get()
                            .go();
  }

  /**
   * Retrieves all of the applications.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Void> retrieveApplications() {
    return start(ApplicationResponse.class, Void.TYPE).uri("/api/application")
                            .get()
                            .go();
  }

  /**
   * Retrieves a single audit log for the given id.
   *
   * @param auditLogId The id of the audit log to retrieve.
   * @return The ClientResponse object.
   */
  public ClientResponse<AuditLogResponse, Errors> retrieveAuditLog(Integer auditLogId) {
    return start(AuditLogResponse.class, Errors.class).uri("/api/system/audit-log")
                            .urlSegment(auditLogId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the daily active user report between the two instants. If you specify an application id, it will only
   * return the daily active counts for that application.
   *
   * @param applicationId (Optional) The application id.
   * @param start The start instant as UTC milliseconds since Epoch.
   * @param end The end instant as UTC milliseconds since Epoch.
   * @return The ClientResponse object.
   */
  public ClientResponse<DailyActiveUserReportResponse, Errors> retrieveDailyActiveReport(UUID applicationId, long start, long end) {
    return start(DailyActiveUserReportResponse.class, Errors.class).uri("/api/report/daily-active-user")
                            .urlParameter("applicationId", applicationId)
                            .urlParameter("start", start)
                            .urlParameter("end", end)
                            .get()
                            .go();
  }

  /**
   * Retrieves the email template for the given id. If you don't specify the id, this will return all of the email templates.
   *
   * @param emailTemplateId (Optional) The id of the email template.
   * @return The ClientResponse object.
   */
  public ClientResponse<EmailTemplateResponse, Void> retrieveEmailTemplate(UUID emailTemplateId) {
    return start(EmailTemplateResponse.class, Void.TYPE).uri("/api/email/template")
                            .urlSegment(emailTemplateId)
                            .get()
                            .go();
  }

  /**
   * Creates a preview of the email template provided in the request. This allows you to preview an email template that
   * hasn't been saved to the database yet. The entire email template does not need to be provided on the request. This
   * will create the preview based on whatever is given.
   *
   * @param request The request that contains the email template and optionally a locale to render it in.
   * @return The ClientResponse object.
   */
  public ClientResponse<PreviewResponse, Errors> retrieveEmailTemplatePreview(PreviewRequest request) {
    return start(PreviewResponse.class, Errors.class).uri("/api/email/template/preview")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Retrieves all of the email templates.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<EmailTemplateResponse, Void> retrieveEmailTemplates() {
    return start(EmailTemplateResponse.class, Void.TYPE).uri("/api/email/template")
                            .get()
                            .go();
  }

  /**
   * Retrieves all of the applications that are currently inactive.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Void> retrieveInactiveApplications() {
    return start(ApplicationResponse.class, Void.TYPE).uri("/api/application")
                            .urlParameter("inactive", true)
                            .get()
                            .go();
  }

  /**
   * Retrieves all of the user actions that are currently inactive.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionResponse, Void> retrieveInactiveUserActions() {
    return start(UserActionResponse.class, Void.TYPE).uri("/api/user-action")
                            .urlParameter("inactive", true)
                            .get()
                            .go();
  }

  /**
   * Retrieves the available integrations.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<IntegrationResponse, Void> retrieveIntegration() {
    return start(IntegrationResponse.class, Void.TYPE).uri("/api/integration")
                            .get()
                            .go();
  }

  /**
   * Retrieves the Public Key configured for verifying JSON Web Tokens (JWT) by the key Id. If the key Id is provided a
   * single public key will be returned if one is found by that id. If the optional parameter key Id is not provided all
   * public keys will be returned.
   *
   * @param keyId (Optional) The id of the public key.
   * @return The ClientResponse object.
   */
  public ClientResponse<PublicKeyResponse, Void> retrieveJWTPublicKey(String keyId) {
    return start(PublicKeyResponse.class, Void.TYPE).uri("/api/jwt/public-key")
                            .urlSegment(keyId)
                            .get()
                            .go();
  }

  /**
   * Retrieves all Public Keys configured for verifying JSON Web Tokens (JWT).
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<PublicKeyResponse, Void> retrieveJWTPublicKeys() {
    return start(PublicKeyResponse.class, Void.TYPE).uri("/api/jwt/public-key")
                            .get()
                            .go();
  }

  /**
   * Retrieves the login report between the two instants. If you specify an application id, it will only return the
   * login counts for that application.
   *
   * @param applicationId (Optional) The application id.
   * @param start The start instant as UTC milliseconds since Epoch.
   * @param end The end instant as UTC milliseconds since Epoch.
   * @return The ClientResponse object.
   */
  public ClientResponse<LoginReportResponse, Errors> retrieveLoginReport(UUID applicationId, long start, long end) {
    return start(LoginReportResponse.class, Errors.class).uri("/api/report/login")
                            .urlParameter("applicationId", applicationId)
                            .urlParameter("start", start)
                            .urlParameter("end", end)
                            .get()
                            .go();
  }

  /**
   * Retrieves the monthly active user report between the two instants. If you specify an application id, it will only
   * return the monthly active counts for that application.
   *
   * @param applicationId (Optional) The application id.
   * @param start The start instant as UTC milliseconds since Epoch.
   * @param end The end instant as UTC milliseconds since Epoch.
   * @return The ClientResponse object.
   */
  public ClientResponse<MonthlyActiveUserReportResponse, Errors> retrieveMonthlyActiveReport(UUID applicationId, long start, long end) {
    return start(MonthlyActiveUserReportResponse.class, Errors.class).uri("/api/report/monthly-active-user")
                            .urlParameter("applicationId", applicationId)
                            .urlParameter("start", start)
                            .urlParameter("end", end)
                            .get()
                            .go();
  }

  /**
   * Retrieves the refresh tokens that belong to the user with the given id.
   *
   * @param userId The id of the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<RefreshResponse, Errors> retrieveRefreshTokens(UUID userId) {
    return start(RefreshResponse.class, Errors.class).uri("/api/jwt/refresh")
                            .urlParameter("userId", userId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the user registration for the user with the given id and the given application id.
   *
   * @param userId The id of the user.
   * @param applicationId The id of the application.
   * @return The ClientResponse object.
   */
  public ClientResponse<RegistrationResponse, Errors> retrieveRegistration(UUID userId, UUID applicationId) {
    return start(RegistrationResponse.class, Errors.class).uri("/api/user/registration")
                            .urlSegment(userId)
                            .urlSegment(applicationId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the registration report between the two instants. If you specify an application id, it will only return
   * the registration counts for that application.
   *
   * @param applicationId (Optional) The application id.
   * @param start The start instant as UTC milliseconds since Epoch.
   * @param end The end instant as UTC milliseconds since Epoch.
   * @return The ClientResponse object.
   */
  public ClientResponse<RegistrationReportResponse, Errors> retrieveRegistrationReport(UUID applicationId, long start, long end) {
    return start(RegistrationReportResponse.class, Errors.class).uri("/api/report/registration")
                            .urlParameter("applicationId", applicationId)
                            .urlParameter("start", start)
                            .urlParameter("end", end)
                            .get()
                            .go();
  }

  /**
   * Retrieves the system configuration.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<SystemConfigurationResponse, Void> retrieveSystemConfiguration() {
    return start(SystemConfigurationResponse.class, Void.TYPE).uri("/api/system-configuration")
                            .get()
                            .go();
  }

  /**
   * Retrieves the totals report. This contains all of the total counts for each application and the global registration
   * count.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<TotalsReportResponse, Void> retrieveTotalReport() {
    return start(TotalsReportResponse.class, Void.TYPE).uri("/api/report/totals")
                            .get()
                            .go();
  }

  /**
   * Retrieves the user for the given id.
   *
   * @param userId The id of the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserResponse, Errors> retrieveUser(UUID userId) {
    return start(UserResponse.class, Errors.class).uri("/api/user")
                            .urlSegment(userId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the user action for the given id. If you pass in null for the id, this will return all of the user
   * actions.
   *
   * @param userActionId (Optional) The id of the user action.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionResponse, Void> retrieveUserAction(UUID userActionId) {
    return start(UserActionResponse.class, Void.TYPE).uri("/api/user-action")
                            .urlSegment(userActionId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the user action reason for the given id. If you pass in null for the id, this will return all of the user
   * action reasons.
   *
   * @param userActionReasonId (Optional) The id of the user action reason.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionReasonResponse, Void> retrieveUserActionReason(UUID userActionReasonId) {
    return start(UserActionReasonResponse.class, Void.TYPE).uri("/api/user-action-reason")
                            .urlSegment(userActionReasonId)
                            .get()
                            .go();
  }

  /**
   * Retrieves all the user action reasons.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionReasonResponse, Void> retrieveUserActionReasons() {
    return start(UserActionReasonResponse.class, Void.TYPE).uri("/api/user-action-reason")
                            .get()
                            .go();
  }

  /**
   * Retrieves all of the user actions.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionResponse, Void> retrieveUserActions() {
    return start(UserActionResponse.class, Void.TYPE).uri("/api/user-action")
                            .get()
                            .go();
  }

  /**
   * Retrieves the user for the given email.
   *
   * @param email The email of the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserResponse, Errors> retrieveUserByEmail(String email) {
    return start(UserResponse.class, Errors.class).uri("/api/user")
                            .urlParameter("email", email)
                            .get()
                            .go();
  }

  /**
   * Retrieves the user for the loginId. The loginId can be either the username or the email.
   *
   * @param loginId The email or username of the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserResponse, Errors> retrieveUserByLoginId(String loginId) {
    return start(UserResponse.class, Errors.class).uri("/api/user")
                            .urlParameter("loginId", loginId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the user for the given username.
   *
   * @param username The username of the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserResponse, Errors> retrieveUserByUsername(String username) {
    return start(UserResponse.class, Errors.class).uri("/api/user")
                            .urlParameter("username", username)
                            .get()
                            .go();
  }

  /**
   * Retrieves all of the comments for the user with the given id.
   *
   * @param userId The id of the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserCommentResponse, Errors> retrieveUserComments(UUID userId) {
    return start(UserCommentResponse.class, Errors.class).uri("/api/user/comment")
                            .urlSegment(userId)
                            .get()
                            .go();
  }

  /**
   * Retrieves the last number of login records for a user.
   *
   * @param userId The id of the user.
   * @param offset The initial record. e.g. 0 is the last login, 100 will be the 100th most recent login.
   * @param limit (Optional, defaults to 10) The number of records to retrieve.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserLoginReportResponse, Errors> retrieveUserLoginReport(UUID userId, int offset, Integer limit) {
    return start(UserLoginReportResponse.class, Errors.class).uri("/api/report/user-login")
                            .urlParameter("userId", userId)
                            .urlParameter("offset", offset)
                            .urlParameter("limit", limit)
                            .get()
                            .go();
  }

  /**
   * Retrieves the webhook for the given id. If you pass in null for the id, this will return all the webhooks.
   *
   * @param webhookId (Optional) The id of the webhook.
   * @return The ClientResponse object.
   */
  public ClientResponse<WebhookResponse, Void> retrieveWebhook(UUID webhookId) {
    return start(WebhookResponse.class, Void.TYPE).uri("/api/webhook")
                            .urlSegment(webhookId)
                            .get()
                            .go();
  }

  /**
   * Retrieves all the webhooks.
   *
   * @return The ClientResponse object.
   */
  public ClientResponse<WebhookResponse, Void> retrieveWebhooks() {
    return start(WebhookResponse.class, Void.TYPE).uri("/api/webhook")
                            .get()
                            .go();
  }

  /**
   * Revokes a single refresh token, all tokens for a user or all tokens for an application. If you provide a user id
   * and an application id, this will delete all the refresh tokens for that user for that application.
   *
   * @param token (Optional) The refresh token to delete.
   * @param userId (Optional) The user id whose tokens to delete.
   * @param applicationId (Optional) The application id of the tokens to delete.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> revokeRefreshToken(String token, UUID userId, UUID applicationId) {
    return start(Void.TYPE, Errors.class).uri("/api/jwt/refresh")
                            .urlParameter("token", token)
                            .urlParameter("userId", userId)
                            .urlParameter("applicationId", applicationId)
                            .delete()
                            .go();
  }

  /**
   * Searches the audit logs with the specified criteria and pagination.
   *
   * @param request The search criteria and pagination information.
   * @return The ClientResponse object.
   */
  public ClientResponse<AuditLogSearchResponse, Void> searchAuditLogs(AuditLogSearchRequest request) {
    return start(AuditLogSearchResponse.class, Void.TYPE).uri("/api/system/audit-log/search")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Retrieves the users for the given ids. If any id is invalid, it is ignored.
   *
   * @param ids The user ids to search for.
   * @return The ClientResponse object.
   */
  public ClientResponse<SearchResponse, Errors> searchUsers(Collection<UUID> ids) {
    return start(SearchResponse.class, Errors.class).uri("/api/user/search")
                            .urlParameter("ids", ids)
                            .get()
                            .go();
  }

  /**
   * Retrieves the users for the given search criteria and pagination.
   *
   * @param request The search criteria and pagination constraints. Fields used: queryString, numberOfResults, startRow,
  *     and sort fields.
   * @return The ClientResponse object.
   */
  public ClientResponse<SearchResponse, Errors> searchUsersByQueryString(SearchRequest request) {
    return start(SearchResponse.class, Errors.class).uri("/api/user/search")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Send an email using an email template id. You can optionally provide <code>requestData</code> to access key value
   * pairs in the email template.
   *
   * @param emailTemplateId The id for the template.
   * @param request The send email request that contains all of the information used to send the email.
   * @return The ClientResponse object.
   */
  public ClientResponse<SendResponse, Errors> sendEmail(UUID emailTemplateId, SendRequest request) {
    return start(SendResponse.class, Errors.class).uri("/api/email/send")
                            .urlSegment(emailTemplateId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
  }

  /**
   * Updates the application with the given id.
   *
   * @param applicationId The id of the application to update.
   * @param request The request that contains all of the new application information.
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Errors> updateApplication(UUID applicationId, ApplicationRequest request) {
    return start(ApplicationResponse.class, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the application role with the given id for the application.
   *
   * @param applicationId The id of the application that the role belongs to.
   * @param roleId The id of the role to update.
   * @param request The request that contains all of the new role information.
   * @return The ClientResponse object.
   */
  public ClientResponse<ApplicationResponse, Errors> updateApplicationRole(UUID applicationId, UUID roleId, ApplicationRequest request) {
    return start(ApplicationResponse.class, Errors.class).uri("/api/application")
                            .urlSegment(applicationId)
                            .urlSegment("role")
                            .urlSegment(roleId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the email template with the given id.
   *
   * @param emailTemplateId The id of the email template to update.
   * @param request The request that contains all of the new email template information.
   * @return The ClientResponse object.
   */
  public ClientResponse<EmailTemplateResponse, Errors> updateEmailTemplate(UUID emailTemplateId, EmailTemplateRequest request) {
    return start(EmailTemplateResponse.class, Errors.class).uri("/api/email/template")
                            .urlSegment(emailTemplateId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the available integrations.
   *
   * @param request The request that contains all of the new integration information.
   * @return The ClientResponse object.
   */
  public ClientResponse<IntegrationResponse, Errors> updateIntegrations(IntegrationRequest request) {
    return start(IntegrationResponse.class, Errors.class).uri("/api/integration")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the registration for the user with the given id and the application defined in the request.
   *
   * @param userId The id of the user whose registration is going to be updated.
   * @param request The request that contains all of the new registration information.
   * @return The ClientResponse object.
   */
  public ClientResponse<RegistrationResponse, Errors> updateRegistration(UUID userId, RegistrationRequest request) {
    return start(RegistrationResponse.class, Errors.class).uri("/api/user/registration")
                            .urlSegment(userId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the system configuration.
   *
   * @param request The request that contains all of the new system configuration information.
   * @return The ClientResponse object.
   */
  public ClientResponse<SystemConfigurationResponse, Errors> updateSystemConfiguration(SystemConfigurationRequest request) {
    return start(SystemConfigurationResponse.class, Errors.class).uri("/api/system-configuration")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the user with the given id.
   *
   * @param userId The id of the user to update.
   * @param request The request that contains all of the new user information.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserResponse, Errors> updateUser(UUID userId, UserRequest request) {
    return start(UserResponse.class, Errors.class).uri("/api/user")
                            .urlSegment(userId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the user action with the given id.
   *
   * @param userActionId The id of the user action to update.
   * @param request The request that contains all of the new user action information.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionResponse, Errors> updateUserAction(UUID userActionId, UserActionRequest request) {
    return start(UserActionResponse.class, Errors.class).uri("/api/user-action")
                            .urlSegment(userActionId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the user action reason with the given id.
   *
   * @param userActionReasonId The id of the user action reason to update.
   * @param request The request that contains all of the new user action reason information.
   * @return The ClientResponse object.
   */
  public ClientResponse<UserActionReasonResponse, Errors> updateUserActionReason(UUID userActionReasonId, UserActionReasonRequest request) {
    return start(UserActionReasonResponse.class, Errors.class).uri("/api/user-action-reason")
                            .urlSegment(userActionReasonId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Updates the webhook with the given id.
   *
   * @param webhookId The id of the webhook to update.
   * @param request The request that contains all of the new webhook information.
   * @return The ClientResponse object.
   */
  public ClientResponse<WebhookResponse, Errors> updateWebhook(UUID webhookId, WebhookRequest request) {
    return start(WebhookResponse.class, Errors.class).uri("/api/webhook")
                            .urlSegment(webhookId)
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .put()
                            .go();
  }

  /**
   * Validates the provided JWT (encoded JWT string) to ensure the token is valid. A valid access token is properly
   * signed and not expired.
   * <p/>
   * This API may be used to verify the JWT as well as decode the encoded JWT into human readable identity claims.
   *
   * @param encodedJWT The encoded JWT (access token).
   * @return The ClientResponse object.
   */
  public ClientResponse<ValidateResponse, Void> validateAccessToken(String encodedJWT) {
    return start(ValidateResponse.class, Void.TYPE).uri("/api/jwt/validate")
                            .authorization("JWT" + encodedJWT)
                            .get()
                            .go();
  }

  /**
   * Confirms a email verification. The id given is usually from an email sent to the user.
   *
   * @param verificationId The verification id sent to the user.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Void> verifyEmail(String verificationId) {
    return start(Void.TYPE, Void.TYPE).uri("/api/user/verify-email")
                            .urlSegment(verificationId)
                            .post()
                            .go();
  }

  /**
   * Confirms a two factor authentication code.
   *
   * @param request The two factor request information.
   * @return The ClientResponse object.
   */
  public ClientResponse<Void, Errors> verifyTwoFactor(TwoFactorRequest request) {
    return start(Void.TYPE, Errors.class).uri("/api/two-factor")
                            .bodyHandler(new JSONBodyHandler(request, objectMapper))
                            .post()
                            .go();
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