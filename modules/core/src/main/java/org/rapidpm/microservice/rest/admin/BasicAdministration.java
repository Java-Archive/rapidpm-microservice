package org.rapidpm.microservice.rest.admin;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static org.rapidpm.microservice.Main.*;

/**
 * Created by svenruppert on 28.08.15.
 */
@Path("/admin/basicadministration")
public class BasicAdministration {

  @GET()
  @Path("{a}")
  @Produces("text/plain")
  public String shutdownNow(@PathParam("a") String authcode) {
    stop(5_000);
    return "code OK";
  }

}
