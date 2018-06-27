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
package org.rapidpm.microservice.optionals.metrics.activeressources;

import com.google.gson.Gson;
import org.rapidpm.microservice.optionals.ActiveUrlsDetector;
import org.rapidpm.microservice.optionals.ActiveUrlsHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Created by svenruppert on 20.11.15.
 * JMS-14 REST Endpoint to get all registered URLS/Servlets/Singletons...
 *
 */
@Path("/info/activeurls")
public class ActiveUrls {
  @GET()
  @Produces(MediaType.APPLICATION_JSON)
  public String listAll() {
    final ActiveUrlsHolder activeUrlsHolder = new ActiveUrlsDetector().detectUrls();
    return new Gson().toJson(activeUrlsHolder, ActiveUrlsHolder.class);
  }
}
