/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.optionals.metrics.health.rest;

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
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/metrics/health")
public class SessionHealth {

  @GET()
  @Produces(MediaType.APPLICATION_JSON)
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
