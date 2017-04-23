[#import "_macros.ftl" as global/]
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

using System;
using System.Collections.Generic;
using Inversoft.Error;
using Inversoft.Passport.Domain;
using Inversoft.Passport.Domain.Search;
using Inversoft.Passport.Domain.Api;
using Inversoft.Passport.Domain.Api.Users;
using Inversoft.Passport.Domain.Api.Report;
using Inversoft.Passport.Domain.Api.EmailApi;
using System.Net;
using Inversoft.Passport.Domain.Api.Jwt;
using Inversoft.Restify;

namespace Com.Inversoft.Passport.Client
{
  /**
   * Client that connects to a Passport server and provides access to the full set of Passport APIs.
   */
  public class PassportClient
  {
    private readonly string apiKey;

    private readonly string baseURL;

    private readonly IWebProxy webProxy;

    public int timeout = 2000;

    public int readWriteTimeout = 2000;

    public PassportClient(string apiKey, string baseURL)
    {
      this.apiKey = apiKey;
      this.baseURL = baseURL;
    }

    public PassportClient(string apiKey, string baseURL, IWebProxy webProxy)
    {
      this.apiKey = apiKey;
      this.baseURL = baseURL;
      this.webProxy = webProxy;
    }

[#list apis as api]
    /**
  [#list api.comments as comment]
     * ${comment}
  [/#list]
     *
  [#list api.params![] as param]
    [#if !param.constant??]
     * @param ${param.name} ${param.comments?join("\n  * ")}
    [/#if]
  [/#list]
     * @return When successful, the response will contain the log of the action. If there was a validation error or any
     * other type of error, this will return the Errors object in the response. Additionally, if Passport could not be
     * contacted because it is down or experiencing a failure, the response will contain an Exception, which could be an
     * IOException.
     */
    public ClientResponse<${api.successResponse}, ${api.errorResponse}> ${api.methodName?capitalize}(${global.methodParameters(api, "csharp")}) {
        return Start<${api.successResponse}, ${api.errorResponse}>().Uri("${api.uri}")
                                      [#if api.authorization??]
                                          .Authorization(${api.authorization})
                                      [/#if]
                                      [#list api.params![] as param]
                                        [#if param.type == "urlSegment"]
                                          .UrlSegment(${(param.constant?? && param.constant)?then(param.value, param.name)})
                                        [#elseif param.type == "urlParameter"]
                                          .UrlParameter("${param.parameterName}", ${(param.constant?? && param.constant)?then(param.value, param.name)})
                                        [#elseif param.type == "body"]
                                          .BodyHandler(new JSONBodyHandler(${param.name}))
                                        [/#if]
                                      [/#list]
                                          .${api.method?capitalize}()
                                          .Go();
    }

[/#list]
    // Start initializes and returns RESTClient
    private RESTClient<T, U> Start<T, U>()
    {
      return new RESTClient<T, U>().Authorization(apiKey)
                                   .SuccessResponseHandler(typeof(T) == typeof(RESTVoid) ? null : new JSONResponseHandler<T>())
                                   .ErrorResponseHandler(typeof(U) == typeof(RESTVoid) ? null : new JSONResponseHandler<U>())
                                   .Url(baseURL)
                                   .Timeout(timeout)
                                   .ReadWriteTimeout(readWriteTimeout)
                                   .Proxy(webProxy);
    }
  }
}