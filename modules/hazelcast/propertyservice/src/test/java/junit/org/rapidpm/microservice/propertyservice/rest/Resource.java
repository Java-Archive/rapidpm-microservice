package junit.org.rapidpm.microservice.propertyservice.rest;


import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/test")
public class Resource {

  public PropertyServiceImpl service = new PropertyServiceImpl();

  @GET()
  @Produces("text/plain")
  public String get() {
    return service.getSingleProperty("test");
  }

}
