package org.rapidpm.microservice.optionals.metrics.health.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.undertow.server.session.SessionManagerStatistics;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.ServletContainer;
import org.rapidpm.microservice.optionals.metrics.health.rest.api.SessionHealthInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by b.bosch on 04.11.2015.
 */
@Path("/metrics/health")
public class SessionHealth {

    @GET()
    @Produces("application/json")
    public String getServletHealth(){
        ServletContainer servletContainer = Servlets.defaultContainer();
        List<SessionHealthInfo> sessionHealthInfos = servletContainer.listDeployments().stream()
                .map(dn -> servletContainer.getDeployment(dn))
                .map(dm -> dm.getDeployment())
                .map(d -> d.getSessionManager())
                .map(sm -> (SessionManagerStatistics) sm)
                .map(SessionHealthInfo::fromStatistics)
                .collect(Collectors.toList());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(sessionHealthInfos);
    }
}
