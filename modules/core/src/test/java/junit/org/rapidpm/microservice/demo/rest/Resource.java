package junit.org.rapidpm.microservice.demo.rest;


//import javax.inject.Inject;

import junit.org.rapidpm.microservice.demo.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by sven on 27.05.15.
 */
@Path("/test")
public class Resource {

  //wird per Request erzeugt.
//  @Inject Service service;
  public Service service = new Service();

  @GET()
  @Produces("text/plain")
  public String get() {
    return "Hello Rest World " + service.doWork();
  }


}
