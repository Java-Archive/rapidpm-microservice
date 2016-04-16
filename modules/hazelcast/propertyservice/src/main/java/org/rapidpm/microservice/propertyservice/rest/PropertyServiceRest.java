package org.rapidpm.microservice.propertyservice.rest;


import org.rapidpm.microservice.propertyservice.impl.PropertyService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/propertyservice")
public class PropertyServiceRest {

  @Inject
  PropertyService propertyService;

  @GET()
  @Path("loadProperties")
  @Produces("text/plain")
  public String loadProperties(@QueryParam("scope") String scope) {
    return propertyService.loadProperties(scope);
  }


  @GET()
  @Path("getSingleProperty")
  @Produces("text/plain")
  public String getSingleProperty(@QueryParam("property") String property) {
    return propertyService.getSingleProperty("test");
  }


}
