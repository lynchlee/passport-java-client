[#import "_macros.ftl" as global/]
[#function parameter_value param]
  [#if param.constant?? && param.constant]
    [#if param.value?starts_with("search")]
      [#return "$" + param.value/] [#-- Hack for the search functions --]
    [#else]
      [#return param.value/]
    [/#if]
  [#else]
    [#return "$" + param.name/]
  [/#if]
[/#function]
<?php
namespace inversoft;

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
class PassportClient
{
  /**
   * @var string
   */
  private $apiKey;

  /**
   * @var string
   */
  private $baseURL;

  /**
   * @var int
   */
  public $connectTimeout = 2000;

  /**
   * @var int
   */
  public $readTimeout = 2000;

  public function __construct($apiKey, $baseURL)
  {
    include_once 'RESTClient.php';
    $this->apiKey = $apiKey;
    $this->baseURL = $baseURL;
  }

[#list apis as api]
  /**
  [#list api.comments as comment]
   * ${comment}
  [/#list]
   *
  [#list api.params![] as param]
    [#if !param.constant??]
   * @param ${global.convertType(param.javaType, "php")} $${param.name} ${param.comments?join("\n  *     ")}
    [/#if]
  [/#list]
   *
   * @return ClientResponse The ClientResponse.
   */
  public function ${api.methodName}(${global.methodParameters(api, "php")})
  {
    return $this->start()->uri("${api.uri}")
    [#if api.authorization??]
        ->authorization(${api.authorization?replace("+ ", ". $")})
    [/#if]
    [#list api.params![] as param]
      [#if param.type == "urlSegment"]
        ->urlSegment(${(param.constant?? && param.constant)?then(param.value, "$" + param.name)})
      [#elseif param.type == "urlParameter"]
        ->urlParameter("${param.parameterName}", ${parameter_value(param)})
      [#elseif param.type == "body"]
        ->bodyHandler(new JSONBodyHandler($${param.name}))
      [/#if]
    [/#list]
        ->${api.method}()
        ->go();
  }

[/#list]
  /**
   * Searches the audit logs with the specified criteria and pagination.
   *
   * @param array $search The search criteria and pagination information.
   *
   * @return ClientResponse The ClientResponse.
   */
  public function searchAuditLogs($search)
  {
    return $this->start()->uri("/api/system/audit-log")
        ->urlParameter("search.user", $search["user"])
        ->urlParameter("search.message", $search["message"])
        ->urlParameter("search.end", $search["end"])
        ->urlParameter("search.start", $search["start"])
        ->urlParameter("search.orderBy", $search["orderBy"])
        ->urlParameter("search.startRow", $search["startRow"])
        ->urlParameter("search.numberOfResults", $search["numberOfResults"])
        ->get()
        ->go();
  }

  /**
   * Retrieves the users for the given search criteria and pagination.
   *
   * @param string $search The search criteria and pagination constraints. Fields used: queryString, numberOfResults,
   *                       and startRow
   *
   * @return ClientResponse The ClientResponse.
   */
  public function searchUsersByQueryString($search)
  {
    $client = $this->start()->uri("/api/user/search")
        ->urlParameter("queryString", $search["queryString"])
        ->urlParameter("numberOfResults", $search["numberOfResults"])
        ->urlParameter("startRow", $search["startRow"]);

    if (isset($search["sortFields"])) {
      $index = 0;
      foreach($search["sortFields"] as $value) {
        $client->urlParameter("sortFields[" . $index . "].name", $value["name"])
               ->urlParameter("sortFields[" . $index . "].missing", $value["missing"])
               ->urlParameter("sortFields[" . $index . "].order", $value["order"]);
        $index++;
      }
    }

    return $client->get()->go();
  }

  private function start()
  {
    $rest = new RESTClient();
    return $rest->authorization($this->apiKey)
        ->url($this->baseURL)
        ->connectTimeout($this->connectTimeout)
        ->readTimeout($this->readTimeout)
        ->successResponseHandler(new JSONResponseHandler())
        ->errorResponseHandler(new JSONResponseHandler());
  }
}