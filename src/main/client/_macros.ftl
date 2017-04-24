[#function convertType type language]
  [#if language == "csharp"]
    [#if type == "UUID"]
      [#return "Guid"/]
    [/#if]
  [#elseif language == "js"]
    [#if type == "UUID" || type == "String"]
      [#return "string"/]
    [#elseif type?starts_with("Collection")]
      [#return "Array"/]
    [#else]
      [#return "Object"/]
    [/#if]
  [#elseif language == "php"]
    [#if type == "UUID" || type == "String"]
      [#return "string"/]
    [#else]
      [#return "array"/]
    [/#if]
  [#elseif language == "ruby"]
    [#if type == "UUID" || type == "String"]
      [#return "string"/]
    [#elseif type?starts_with("Collection")]
      [#return "Array"/]
    [#else]
      [#return "OpenStruct, Hash"/]
    [/#if]
  [/#if]
  [#return type/]
[/#function]

[#function methodParameters api language]
  [#local result = []]
  [#list api.params![] as param]
    [#if !param.constant??]
      [#if language == "php"]
        [#local result = result + ["$" + param.name]/]
      [#elseif language == "js"]
        [#local result = result + [param.name]/]
      [#elseif language == "python" || language == "ruby"]
        [#local result = result + [camel_to_underscores(param.name)]/]
      [#else]
        [#local result = result + [convertType(param.javaType, language) + " " + param.name]/]
      [/#if]
    [/#if]
  [/#list]
  [#return result?join(", ")/]
[/#function]