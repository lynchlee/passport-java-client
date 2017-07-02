[#function convertType type language]
  [#if language == "csharp"]
    [#if type == "UUID"]
      [#return "Guid"/]
    [#elseif type == "boolean"]
      [#return "bool"/]
    [#elseif type == "Integer"]
      [#return "int"/]
    [#elseif type == "Long"]
      [#return "long"/]
    [#elseif type == "Void"]
      [#return "RESTVoid"/]
    [#elseif type?starts_with("Collection")]
      [#return type?replace("Collection", "ICollection")?replace("UUID", "Guid")/]
    [#elseif type == "String"]
      [#return "string"/]
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

[#function convertValue param language]
  [#if language == "ruby"]
    [#if param.name == "end"]
      [#return "_end"/]
    [#else]
      [#return (param.constant?? && param.constant)?then(param.value, camel_to_underscores(param.name))/]
    [/#if]
  [/#if]
  [#return param.value/]
[/#function]

[#function optional param language]
  [#if language == "js"]
    [#return param.comments[0]?starts_with("(Optional)")?then("?", "")/]
  [#else]
    [#return ""/]
  [/#if]
[/#function]

[#function methodParameters api language]
  [#local result = []]
  [#list api.params![] as param]
    [#if !param.constant??]
      [#if language == "php"]
        [#local result = result + ["$" + param.name]/]
      [#elseif language == "js"]
        [#local result = result + [param.name]/]
      [#elseif language == "python"]
        [#local result = result + [camel_to_underscores(param.name)]/]
      [#elseif language == "ruby"]
        [#if param.name == "end"]
          [#local result = result + ["_end"]/]
        [#else]
          [#local result = result + [camel_to_underscores(param.name)]/]
        [/#if]
      [#elseif language == "csharp" && param.comments[0]?starts_with("(Optional)") && param.javaType != "String"]
        [#local result = result + [convertType(param.javaType, language) + "? " + param.name]/]
      [#else]
        [#local result = result + [convertType(param.javaType, language) + " " + param.name]/]
      [/#if]
    [/#if]
  [/#list]
  [#return result?join(", ")/]
[/#function]