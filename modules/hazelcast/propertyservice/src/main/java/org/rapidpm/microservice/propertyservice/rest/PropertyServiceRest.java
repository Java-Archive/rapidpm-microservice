package org.rapidpm.microservice.propertyservice.rest;


import com.google.gson.Gson;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.propertyservice.api.PropertyService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/propertyservice")
public class PropertyServiceRest {

  @Inject
  PropertyService propertyService;

  private final Gson gson = new Gson();

  public PropertyServiceRest() {
    DI.registerClassForScope(PropertyService.class, JVMSingletonInjectionScope.class.getSimpleName());
  }

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
  @Produces("application/json")
  public String getIndexOfLoadedProperties() {
    return gson.toJson(propertyService.getIndexOfLoadedProperties());
  }

  @GET()
  @Path("getIndexOfScope")
  @Produces("application/json")
  public String getIndexOfScope(@QueryParam("scope") String scope) {
    return gson.toJson(propertyService.getIndexOfScope(scope));
  }

  @GET()
  @Path("getPropertiesOfScope")
  @Produces("application/json")
  public String getPropertiesOfScope(@QueryParam("scope") String scope) {
    return gson.toJson(propertyService.getPropertiesOfScope(scope));
  }

}
