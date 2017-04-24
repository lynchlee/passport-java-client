[#import "_macros.ftl" as global/]
#
# Copyright (c) 2016, Inversoft Inc., All Rights Reserved
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

import requests

class PassportClient:
    """The PassportClient provides easy access to the Passport API."""

    def __init__(self, api_key, base_url):
        """Constructs a new PassportClient.

        Attributes:
            api_key: A string representing the API used to authenticate the API call to Passport
            base_url: A string representing the URL use to access Passport
        """
        self.api_key = api_key
        self.base_url = base_url

[#list apis as api]
    def ${camel_to_underscores(api.methodName)}(${global.methodParameters(api, "python")}):
        """
        ${api.comments?join("\n        ")}

        Attributes:
        [#list api.params![] as param]
          [#if !param.constant??]
            ${camel_to_underscores(param.name)}: ${param.comments?join("\n                    ")}
          [/#if]
        [/#list]
        """
        return self.start().uri('${api.uri}') \
            [#if api.authorization??]
                .authorization(${api.authorization})
            [/#if]
            [#list api.params![] as param]
              [#if param.type == "urlSegment"]
                .url_segment(${(param.constant?? && param.constant)?then(param.value, camel_to_underscores(param.name))}) \
              [#elseif param.type == "urlParameter"]
                .url_parameter('${param.parameterName}', ${(param.constant?? && param.constant)?then(param.value, camel_to_underscores(param.name))}) \
              [#elseif param.type == "body"]
                .body_handler(JSONBodyHandler(${camel_to_underscores(param.name)})) \
              [/#if]
            [/#list]
                .${api.method}() \
                .go()

[/#list]
    def search_for_users_by_search_criteria(self, search):
        client = self.start().uri('/api/user/search') \
                .url_parameter('queryString', search.queryString) \
                .url_parameter('numberOfResults', search.numberOfResults) \
                .url_parameter('startRow', search.startRow)

        if search.sortFields:
            for i, value in enumerate(search.sortFields:
                client.urlParameter("sortFields[" + i + "].name", value.name)
                    .urlParameter("sortFields[" + i + "].missing", value.missing)
                    .urlParameter("sortFields[" + i + "].order", value.order)

        return client.get().go()

    def start(self):
        return RESTClient().authorization(self.api_key).url(self.base_url)
