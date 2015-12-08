package org.rapidpm.microservice.optionals;


import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.header.ActiveUrlsHolder;
import org.rapidpm.microservice.rest.JaxRsActivator;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.Path;
import java.util.Set;

/**
 * Created by svenruppert on 20.11.15.
 */
public class ActiveUrlsDetector {


  public ActiveUrlsHolder detectUrls() {
    final ActiveUrlsHolder activeUrlsHolder = new ActiveUrlsHolder();

    //print all URLs
    final Set<Class<?>> typesAnnotatedWith = DI.getTypesAnnotatedWith(WebServlet.class);

    long servletCount = typesAnnotatedWith.stream()
        .map(aClass1 -> aClass1.getAnnotation(WebServlet.class))
        .filter(ws -> ws.urlPatterns().length > 0)
        .count();

    activeUrlsHolder.setServletCount(servletCount);

    final JaxRsActivator jaxRsActivator = new JaxRsActivator();
    final Set<Class<?>> restClasses = jaxRsActivator.getClasses();
    final Set<Object> singletonClasses = jaxRsActivator.getSingletons();

    final String realServletPort = System.getProperty(Main.SERVLET_PORT_PROPERTY, Main.DEFAULT_SERVLET_PORT + "");
    final String realServletHost = System.getProperty(Main.SERVLET_HOST_PROPERTY, Main.DEFAULT_HOST);
    for (Class<?> aClass : typesAnnotatedWith) {
      final WebServlet annotation = aClass.getAnnotation(WebServlet.class);
      final String[] urlPatterns = annotation.urlPatterns();
      for (String urlPattern : urlPatterns) {
        String url = "http://" + realServletHost + ":" + realServletPort + Main.MYAPP + urlPattern;
        activeUrlsHolder.addServletUrl(url);
      }
    }

    final String realRestPort = System.getProperty(Main.REST_PORT_PROPERTY, Main.DEFAULT_REST_PORT + "");
    final String realRestHost = System.getProperty(Main.REST_HOST_PROPERTY, Main.DEFAULT_HOST);

    for (Class<?> aClass : restClasses) {
      final Path annotation = aClass.getAnnotation(Path.class);
      final String urlPattern = annotation.value();
      String url = "http://" + realRestHost + ":" + realRestPort + Main.CONTEXT_PATH_REST + urlPattern;
      activeUrlsHolder.addRestUrl(url);
    }

    for (Object aClass : singletonClasses) {
      final Path annotation = aClass.getClass().getAnnotation(Path.class);
      final String urlPattern = annotation.value();
      String url = "http://" + realRestHost + ":" + realRestPort + Main.CONTEXT_PATH_REST + urlPattern;
      activeUrlsHolder.addSingletonUrl(url);
    }
    return activeUrlsHolder;
  }


}
