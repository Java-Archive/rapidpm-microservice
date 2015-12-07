package org.rapidpm.microservice.test;

import org.jboss.resteasy.test.TestPortProvider;

import javax.ws.rs.Path;

/**
 * Created by svenruppert on 31.08.15.
 */
public class RestUtils {

  public String generateBasicReqURL(Class restClass, String restAppPath) {
    if (restClass.isAnnotationPresent(Path.class)) {
      final Path path = (Path) restClass.getAnnotation(Path.class);
      final String ressourcePath = path.value();
      return TestPortProvider.generateURL(restAppPath + ressourcePath);
    }
    throw new RuntimeException("Class without Path Annotation " + restClass);
  }

}
