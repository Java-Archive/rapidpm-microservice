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

package junit.org.rapidpm.microservice.optionals.index.v004.server;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.optionals.index.v004.api.IndexOfTypeLoggerEvent;
import junit.org.rapidpm.microservice.optionals.index.v004.api.LoggerEvent;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

@Path("/index/idx/loggerevent")
public class LoggerEventREST {

  public static final Type LOGGER_EVENT_LIST_TYPE = LoggerEvent[].class;
  public static final String INDEX_NAME = IndexOfTypeLoggerEvent.class.getSimpleName();

  public static final String QUERY = "query";
  public static final String QP_QUERY = "query";
  public static final String QP_LOGGER_EVENT = "event";
  public static final String INSERT = "insert";
  public static final String OK = "OK";

  @Inject IndexStore indexStore;

  @GET()
  @Path(QUERY)
  @Produces(MediaType.APPLICATION_JSON)
  public String query(@QueryParam(QP_QUERY) String query) {
    final LoggerEventIndex index = indexStore.getIndex(INDEX_NAME);
    final List<LoggerEvent> events = index.query(query);
    return new Gson().toJson(events.toArray(new LoggerEvent[0]), LOGGER_EVENT_LIST_TYPE);
  }

  @GET()
  @Path(INSERT)
  @Produces(MediaType.APPLICATION_JSON)
  public String insert(@QueryParam(QP_LOGGER_EVENT)
                       final String loggerEventJSON) {
    final Gson gson = new Gson();

    final Decoder decoder = Base64.getDecoder();

    final LoggerEvent event = gson.fromJson(new String(decoder.decode(loggerEventJSON)), (Type) LoggerEvent.class);
    final LoggerEventIndex index = indexStore.getIndex(INDEX_NAME);
    index.addElement(event);
    return OK;
  }

  @PostConstruct
  public void postConstruct() {
    if (indexStore.containsIndex(INDEX_NAME)) {
      // all fine....
    } else {
      final LoggerEventIndex loggerEventIndex = LoggerEventIndex.newBuilder()
          .withIndexName(INDEX_NAME)
          .build();
      indexStore.addIndexOfType(INDEX_NAME, loggerEventIndex);
    }
  }

}
