package org.rapidpm.microservice.optionals.metrics.health.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.undertow.server.session.SessionManager;
import io.undertow.server.session.SessionManagerStatistics;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.Deployment;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import org.rapidpm.microservice.optionals.metrics.health.rest.api.SessionHealthInfo;
import org.rapidpm.microservice.optionals.metrics.health.rest.api.SessionHealthInfoJsonConverter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by b.bosch on 04.11.2015.
 */
@Path("/metrics/health")
public class SessionHealth {

  @GET()
  @Produces("application/json")
  public String getServletHealth() {
    ServletContainer servletContainer = Servlets.defaultContainer();
    List<SessionHealthInfo> sessionHealthInfos = servletContainer
        .listDeployments()
        .stream()
        .map(servletContainer::getDeployment)
        .map(DeploymentManager::getDeployment)
        .map(Deployment::getSessionManager)
        .map(sm -> (SessionManager & SessionManagerStatistics) sm)
        .map(SessionHealthInfo::new)
        .collect(Collectors.toList());
    return new SessionHealthInfoJsonConverter().toJson(sessionHealthInfos);
  }
}
