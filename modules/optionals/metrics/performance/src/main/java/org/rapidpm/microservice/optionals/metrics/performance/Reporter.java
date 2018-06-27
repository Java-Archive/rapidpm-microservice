/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.microservice.optionals.metrics.performance;

import org.rapidpm.proxybuilder.core.metrics.RapidPMMetricsRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Path("/metrics/performance/reporter")
public class Reporter {

  public static final String STOP_JMXREPORTER = "stopJMXReporter";
  public static final String START_JMXREPORTER = "startJMXReporter";
  public static final String STOP_CONSOLEREPORTER = "stopConsoleReporter";
  public static final String START_CONSOLEREPORTER = "startConsoleReporter";

  @GET()
  @Path(START_JMXREPORTER)
  @Produces(MediaType.APPLICATION_JSON)
  public String startJMXReporter() {
    RapidPMMetricsRegistry
        .getInstance().startJmxReporter();
    return result(START_JMXREPORTER);
  }

  private String result(final String methodPath) {
    final String timestamp = LocalDateTime.now()
        .format(DateTimeFormatter.ISO_DATE_TIME);
    return "{\"methodPath\":\"" + methodPath + "\",\"timestamp\":\"" + timestamp + "\"}";
  }

  @GET()
  @Path(STOP_JMXREPORTER)
  @Produces(MediaType.APPLICATION_JSON)
  public String stopJMXReporter() {
    RapidPMMetricsRegistry
        .getInstance().stopJmxReporter();
    return result(STOP_JMXREPORTER);
  }

  @GET()
  @Path(START_CONSOLEREPORTER)
  @Produces(MediaType.APPLICATION_JSON)
  public String startConsoleReporter() {
    RapidPMMetricsRegistry
        .getInstance()
        .startConsoleReporter();
    return result(START_CONSOLEREPORTER);
  }

  @GET()
  @Path(STOP_CONSOLEREPORTER)
  @Produces(MediaType.APPLICATION_JSON)
  public String stopConsoleReporter() {
    RapidPMMetricsRegistry
        .getInstance()
        .stopConsoleReporter();

    System.out.println("ConsoleReporter was stopped - " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    return result(STOP_CONSOLEREPORTER);
  }

}
