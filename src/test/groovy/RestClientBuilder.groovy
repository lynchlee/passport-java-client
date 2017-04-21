import freemarker.cache.FileTemplateLoader
import freemarker.ext.beans.BeansWrapperBuilder
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateException

import java.nio.file.Path
import java.nio.file.Paths

/*
 * Copyright (c) 2017, Inversoft Inc., All Rights Reserved
 *
 * <pre>
 *     $ groovy -cp ~/.savant/cache/org/freemarker/freemarker/2.3.23/freemarker-2.3.23.jar RestClientBuilder.groovy
 * </pre>
 */
class RestClientBuilder {

  private static final Configuration config

  static {
    BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_23)
    builder.setExposeFields(true)
    builder.setSimpleMapWrapper(true)

    config = new Configuration(Configuration.VERSION_2_3_23)
    config.setDefaultEncoding("UTF-8")
    config.setNumberFormat("computer")
    config.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX)
    config.setObjectWrapper(builder.build())
    config.setNumberFormat("computer")
    try {
      config.setTemplateLoader(new FileTemplateLoader(new File("/")))
    } catch (IOException e) {
      throw new RuntimeException(e)
    }
  }

  static void main(String[] args) {
    StringWriter writer = new StringWriter()

    def apis = [ 'apis' : [] ]
    def jsonSlurper = new groovy.json.JsonSlurper()
    new File("../../../src/main/api").eachFile { f ->
      if (f.isFile()) {
        def name = f.getName()
        apis['apis'] << jsonSlurper.parseText(f.getText())
      }
    }

    Path javaTemplate = Paths.get("../../../src/main/api/clients/java.client.ftl")
    Template template = config.getTemplate(javaTemplate.toAbsolutePath().toString())
    try {
      template.process(apis, writer)
      println writer.toString()
    } catch (TemplateException e) {
      throw new RuntimeException(e)
    }

  }
}