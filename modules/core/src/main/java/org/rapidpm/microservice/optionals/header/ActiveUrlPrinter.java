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

    activeUrlsHolder.getServletUrls().forEach(System.out::println);

    System.out.println("");
    System.out.println("List RestEndpoint - URLs");
    activeUrlsHolder.getRestUrls().forEach(System.out::println);

    System.out.println("");
    System.out.println("List RestEndpoints (Singletons) URLs");
    activeUrlsHolder.getSingletonUrls().forEach(System.out::println);
  }
}
