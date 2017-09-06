package org.rapidpm.microservice.rest.optionals.properties.rest;

import com.google.gson.Gson;
import org.rapidpm.microservice.rest.optionals.properties.api.PropertiesStore;
import org.rapidpm.microservice.rest.optionals.properties.api.AuthenticationService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

/**
 * Copyright (C) 2010 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by RapidPM - Team on 16.12.2016.
 */
@Path("/propertyservice")
public class PropertyServiceRestEndpoint {

  public static final String NOT_FOUND = "property not found";
  @Inject
  PropertiesStore propertiesStore;
  @Inject
  AuthenticationService authenticationServiceImpl;

  private Gson gson = new Gson();

  @GET
  @Path("getproperty")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.TEXT_PLAIN)
  public Response getProperty(@Context HttpServletRequest requestContext, @QueryParam("name") String name) {
    if (authenticate(requestContext)) {
      return Response.serverError().entity(String.format("client <%s> not allowed", requestContext.getRemoteAddr())).build();
    }

    final Optional<String> property = propertiesStore.getProperty(name);
    return property.isPresent() ?
        Response.ok(property.get(), MediaType.TEXT_PLAIN).build() :
        Response.serverError().entity(NOT_FOUND).build();
  }

  @GET
  @Path("getnamespace")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getNamespace(@Context HttpServletRequest requestContext, @QueryParam("namespace") String name) {
    if (authenticate(requestContext)) {
      return Response.serverError().entity(String.format("client <%s> not allowed", requestContext.getRemoteAddr())).build();
    }

    final Map<String, String> property = propertiesStore.getPropertiesOfNamespace(name);
    final String result = gson.toJson(property, Map.class);
    return Response.ok().entity(result).build();
  }


  private boolean authenticate(@Context HttpServletRequest requestContext) {
    return authenticationServiceImpl.isActive() &&
           ! authenticationServiceImpl.authenticate(requestContext.getRemoteAddr());
  }


}
