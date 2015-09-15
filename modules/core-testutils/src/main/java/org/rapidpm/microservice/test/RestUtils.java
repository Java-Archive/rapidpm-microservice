package org.rapidpm.microservice.test;

import org.jboss.resteasy.test.TestPortProvider;

import javax.ws.rs.Path;

/**
 * Created by svenruppert on 31.08.15.
 */
public class RestUtils {

  public String generateBasicReqURL(Class restClass, String restAppPath) {
    //final String restAppPath = Main.CONTEXT_PATH_REST;

    if (restClass.isAnnotationPresent(Path.class)) {
      final Path path = (Path) restClass.getAnnotation(Path.class);
      final String ressourcePath = path.value();
      final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
      return generateURL;
    }
    throw new RuntimeException("Class without Path Annotation " + restClass);
  }


}
