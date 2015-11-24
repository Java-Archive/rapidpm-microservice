package org.rapidpm.microservice.optionals.header;

/**
 * Created by svenruppert on 04.11.15.
 */
public class ActiveUrlPrinter {

  public void printActiveURLs(ActiveUrlsHolder activeUrlsHolder) {


    System.out.println("================= Deployment Summary ================= ");
    System.out.println("Sum Servlets                   = " + activeUrlsHolder.getServletCounter());
    System.out.println("Sum RestEndpoints              = " + activeUrlsHolder.getRestUrls().size());
    System.out.println("Sum RestEndpoints (Singletons) = " + activeUrlsHolder.getSingletonUrls().size());
    System.out.println("================= Deployment Summary ================= ");

    System.out.println("");
    System.out.println("List Servlet - URLs ");

//    final String realServletPort = System.getProperty(Main.SERVLET_PORT_PROPERTY, Main.DEFAULT_SERVLET_PORT + "");
//    final String realServletHost = System.getProperty(Main.SERVLET_HOST_PROPERTY, Main.DEFAULT_HOST);
//    for (Class<?> aClass : typesAnnotatedWith) {
//      final WebServlet annotation = aClass.getAnnotation(WebServlet.class);
//      final String[] urlPatterns = annotation.urlPatterns();
//      for (String urlPattern : urlPatterns) {
////        System.out.println("Class = " + aClass.getName());
//        String url = "http://" + realServletHost + ":" + realServletPort + Main.MYAPP + urlPattern;
//        System.out.println("url = " + url);
//      }
//    }
    activeUrlsHolder.getServletUrls().forEach(System.out::println);


    System.out.println("");
    System.out.println("List RestEndpoint - URLs");
//    final String realRestPort = System.getProperty(Main.REST_PORT_PROPERTY, Main.DEFAULT_REST_PORT + "");
//    final String realRestHost = System.getProperty(Main.REST_HOST_PROPERTY, Main.DEFAULT_HOST);
//    for (Class<?> aClass : restClasses) {
//      final Path annotation = aClass.getAnnotation(Path.class);
//      final String urlPattern = annotation.value();
////      System.out.println("Class = " + aClass.getName());
//      String url = "http://" + realRestHost + ":" + realRestPort + Main.CONTEXT_PATH_REST + urlPattern;
//      System.out.println("url = " + url);
//
//    }
    activeUrlsHolder.getRestUrls().forEach(System.out::println);


    System.out.println("");
    System.out.println("List RestEndpoints (Singletons) URLs");
    activeUrlsHolder.getSingletonUrls().forEach(System.out::println);
//    for (Object aClass : singletonClasses) {
//      final Path annotation = aClass.getClass().getAnnotation(Path.class);
//      final String urlPattern = annotation.value();
////      System.out.println("Class = " + aClass.getName());
//      String url = "http://" + realRestHost + ":" + realRestPort + Main.CONTEXT_PATH_REST + urlPattern;
//      System.out.println("url = " + url);
//    }
  }
}
