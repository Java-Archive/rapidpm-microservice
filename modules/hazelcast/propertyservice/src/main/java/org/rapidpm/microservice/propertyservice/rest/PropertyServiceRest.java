package org.rapidpm.microservice.propertyservice.rest;


import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/propertyservice")
public class PropertyServiceRest {

  @Inject
  PropertyServiceImpl propertyService;

  public PropertyServiceRest(String source) {
    propertyService.init(source);
  }

  @GET()
  @Path("getSingleProperty")
  @Produces("text/plain")
  public String get(@QueryParam("property") String property) {
    return propertyService.getSingleProperty("test");
  }


}
