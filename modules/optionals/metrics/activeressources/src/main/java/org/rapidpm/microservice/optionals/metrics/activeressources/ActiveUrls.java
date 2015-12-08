package org.rapidpm.microservice.optionals.metrics.activeressources;

import com.google.gson.Gson;
import org.rapidpm.microservice.optionals.ActiveUrlsDetector;
import org.rapidpm.microservice.optionals.header.ActiveUrlsHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 * Created by svenruppert on 20.11.15.
 * JMS-14 REST Endpoint to get all registered URLS/Servlets/Singletons...
 *
 */
@Path("/info/activeurls")
public class ActiveUrls {
  @GET()
  @Produces("application/json")
  public String listAll() {
    final ActiveUrlsHolder activeUrlsHolder = new ActiveUrlsDetector().detectUrls();
    return new Gson().toJson(activeUrlsHolder, ActiveUrlsHolder.class);
  }
}
