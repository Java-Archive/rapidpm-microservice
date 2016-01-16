package org.rapidpm.microservice.test;

import org.jboss.resteasy.test.TestPortProvider;

import javax.ws.rs.Path;

/**
 * Created by Sven Ruppert on 31.08.15.
 */
public class RestUtils {

  public static final String RESTEASY_PORT_PROPERTY = "org.jboss.resteasy.port";
  public static final String RESTEASY_HOST_PROPERTY = "org.jboss.resteasy.host";


  public void setRestEasyPropertys(String host, String port) {
    System.setProperty(RESTEASY_PORT_PROPERTY, port);
    System.setProperty(RESTEASY_HOST_PROPERTY, host);
  }

  public void setRestEasyPropertys(String port) {
    System.setProperty(RESTEASY_PORT_PROPERTY, port);
  }


  public String generateBasicReqURL(Class restClass, String restAppPath) {
    if (restClass.isAnnotationPresent(Path.class)) {
      final Path path = (Path) restClass.getAnnotation(Path.class);
      final String ressourcePath = path.value();
      return TestPortProvider.generateURL(restAppPath + ressourcePath);
    }
    throw new RuntimeException("Class without Path Annotation " + restClass);
  }

}
