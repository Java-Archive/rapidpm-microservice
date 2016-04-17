package org.rapidpm.microservice.propertyservice.rest;


import org.rapidpm.microservice.propertyservice.impl.PropertyService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Map;
import java.util.Set;

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
    return propertyService.getSingleProperty(property);
  }

  @GET()
  @Path("getIndexOfLoadedProperties")
  @Produces("text/plain")
  public Set<String> getIndexOfLoadedProperties() {
    return propertyService.getIndexOfLoadedProperties();
  }

  @GET()
  @Path("getIndexOfScope")
  @Produces("text/plain")
  public Set<String> getIndexOfScope(@QueryParam("scope") String scope) {
    return propertyService.getIndexOfScope(scope);
  }

  @GET()
  @Path("getPropertiesOfScope")
  @Produces("text/plain")
  public Map<String, String> getPropertiesOfScope(@QueryParam("scope") String scope) {
    return propertyService.getPropertiesOfScope(scope);
  }

}
