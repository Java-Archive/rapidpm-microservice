package org.rapidpm.microservice.optionals.header;

import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.rest.JaxRsActivator;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.Path;
import java.util.Set;

/**
 * Created by svenruppert on 04.11.15.
 */
public class ActiveUrlPrinter {

  public void printActiveURLs(final JaxRsActivator jaxRsActivator) {
    //print all URLs
    final Set<Class<?>> typesAnnotatedWith = DI.getTypesAnnotatedWith(WebServlet.class);
    final Set<Class<?>> restClasses = jaxRsActivator.getClasses();
    final Set<Object> singletonClasses = jaxRsActivator.getSingletons();

    System.out.println("================= Deployment Summary ================= ");
    System.out.println("Sum Servlets                   = " + typesAnnotatedWith.size());
    System.out.println("Sum RestEndpoints              = " + restClasses.size());
    System.out.println("Sum RestEndpoints (Singletons) = " + singletonClasses.size());
    System.out.println("================= Deployment Summary ================= ");


    System.out.println("");
    System.out.println("List Servlet - URLs ");

    final String realServletPort = System.getProperty(Main.SERVLET_PORT_PROPERTY, Main.DEFAULT_SERVLET_PORT + "");
    final String realServletHost = System.getProperty(Main.SERVLET_HOST_PROPERTY, Main.DEFAULT_HOST);
    for (Class<?> aClass : typesAnnotatedWith) {
      final WebServlet annotation = aClass.getAnnotation(WebServlet.class);
      final String[] urlPatterns = annotation.urlPatterns();
      for (String urlPattern : urlPatterns) {
//        System.out.println("Class = " + aClass.getName());
        String url = "http://" + realServletHost + ":" + realServletPort + Main.MYAPP + "/" + urlPattern;
        System.out.println("url = " + url);
      }
    }
    System.out.println("");
    System.out.println("List RestEndpoint - URLs");
    final String realRestPort = System.getProperty(Main.REST_PORT_PROPERTY, Main.DEFAULT_REST_PORT + "");
    final String realRestHost = System.getProperty(Main.REST_HOST_PROPERTY, Main.DEFAULT_HOST);
    for (Class<?> aClass : restClasses) {
      final Path annotation = aClass.getAnnotation(Path.class);
      final String urlPattern = annotation.value();
//      System.out.println("Class = " + aClass.getName());
      String url = "http://" + realRestHost + ":" + realRestPort + Main.CONTEXT_PATH_REST + "/" + urlPattern;
      System.out.println("url = " + url);

    }
    System.out.println("");
    System.out.println("List RestEndpoints (Singletons) URLs");
    for (Object aClass : singletonClasses) {
      final Path annotation = aClass.getClass().getAnnotation(Path.class);
      final String urlPattern = annotation.value();
//      System.out.println("Class = " + aClass.getName());
      String url = "http://" + realRestHost + ":" + realRestPort + Main.CONTEXT_PATH_REST + "/" + urlPattern;
      System.out.println("url = " + url);

    }
  }
}
