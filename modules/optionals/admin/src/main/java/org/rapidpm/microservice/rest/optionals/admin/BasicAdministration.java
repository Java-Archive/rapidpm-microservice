package org.rapidpm.microservice.rest.optionals.admin;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static org.rapidpm.microservice.Main.stop;

/**
 * Created by Sven Ruppert on 28.08.15.
 */
@Path("/admin/basicadministration")
public class BasicAdministration {

  @GET()
  @Path("{a}")
  @Produces("text/plain")
  public String shutdownNow(@PathParam("a") String timeout) {
    stop(5_000);
    return "code OK";
  }

}
