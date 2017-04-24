[#import "_macros.ftl" as global/]
#
# Copyright (c) 2016-2017, Inversoft Inc., All Rights Reserved
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
# either express or implied. See the License for the specific
# language governing permissions and limitations under the License.
#

module Inversoft
  #
  # This class is the the Ruby client library for the Passport User Platform {https://www.inversoft.com/products/identity-user-management}
  #
  # Each method on this class calls one of the APIs for Passport. In most cases, the methods will take either a Hash, an
  # OpenStruct or any object that can be safely converted to JSON that conforms to the Passport API interface. Likewise,
  # most methods will return an OpenStruct that contains the response JSON from Passport.
  #
  # noinspection RubyInstanceMethodNamingConvention,RubyTooManyMethodsInspection,RubyParameterNamingConvention
  class PassportClient
    attr_accessor :api_key, :base_url, :connect_timeout, :read_timeout

    def initialize(api_key, base_url)
      @api_key = api_key
      @base_url = base_url
      @connect_timeout = 1000
      @read_timeout = 2000
    end

[#list apis as api]
    #
  [#list api.comments as comment]
    # ${comment}
  [/#list]
    #
  [#list api.params![] as param]
    [#if !param.constant??]
    # @param ${camel_to_underscores(param.name)} [${global.convertType(param.javaType, "ruby")}] ${param.comments?join("\n    #     ")}
    [/#if]
  [/#list]
    # @return [Inversoft::ClientResponse] The ClientResponse object.
    #
    def ${camel_to_underscores(api.methodName)}(${global.methodParameters(api, "ruby")})
      start.uri('${api.uri}')
      [#if api.authorization??]
           .authorization(${api.authorization})
      [/#if]
      [#list api.params![] as param]
        [#if param.type == "urlSegment"]
           .url_segment(${(param.constant?? && param.constant)?then(param.value, camel_to_underscores(param.name))})
        [#elseif param.type == "urlParameter"]
           .url_parameter('${param.parameterName}', ${(param.constant?? && param.constant)?then(param.value, camel_to_underscores(param.name))})
        [#elseif param.type == "body"]
           .body_handler(Inversoft::JSONBodyHandler.new(${camel_to_underscores(param.name)}))
        [/#if]
      [/#list]
           .${api.method}()
           .go()
    end

[/#list]
    #
    # Searches the audit logs with the specified criteria and pagination.
    #
    # @param search [OpenStruct, Hash] The search criteria and pagination information.
    #
    # @return [Inversoft::ClientResponse] The ClientResponse object.
    #
    def search_users_by_query_string(search)
      return start.uri("/api/system/audit-log")
          .urlParameter("search.user", search["user"])
          .urlParameter("search.message", search["message"])
          .urlParameter("search.end", search["end"])
          .urlParameter("search.start", search["start"])
          .urlParameter("search.orderBy", search["orderBy"])
          .urlParameter("search.startRow", search["startRow"])
          .urlParameter("search.numberOfResults", search["numberOfResults"])
          .get
          .go
    end

    #
    # Retrieves the users for the given search criteria and pagination.
    #
    # @param search [OpenStruct, Hash] The search criteria and pagination constraints. Fields used: queryString, numberOfResults, and startRow
    #
    # @return [Inversoft::ClientResponse] The ClientResponse object.
    #
    def search_users_by_query_string(search)
      client = start.uri("/api/user/search")
          .url_parameter("queryString", search["queryString"])
          .url_parameter("numberOfResults", search["numberOfResults"])
          .url_parameter("startRow", search["startRow"])

      if search["sortFields"]
        index = 0
        search["sortFields"].each_with_index |value, index|
          client.url_parameter("sortFields[" + index + "].name", value["name"])
              .url_parameter("sortFields[" + index + "].missing", value["missing"])
              .url_parameter("sortFields[" + index . "].order", value["order"])
          index++
        end
      end

      return client.get.go
    end

    #
    # Starts the HTTP call
    #
    # @return [RESTClient] The RESTClient
    #
    private
    def start
      RESTClient.new
          .authorization(@api_key)
          .success_response_handler(Inversoft::JSONResponseHandler.new(OpenStruct))
          .error_response_handler(Inversoft::JSONResponseHandler.new(OpenStruct))
          .url(@base_url)
          .connect_timeout(@connect_timeout)
          .read_timeout(@read_timeout)
    end
  end
end

