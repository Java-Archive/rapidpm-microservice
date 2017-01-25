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

package org.rapidpm.microservice.optionals.index;

import com.google.gson.Gson;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.format.DateTimeFormatter;

@Path("/index/management")
public class IndexManagement {

  public static final String LIST_ALL_INDICES = "listAllIndices";
  public static final String REMOVE_INDEX = "removeIndex";
  public static final String REMOVE_INDEX_QUERYPARAM = "indexName";
  public static final String CONTAINS_INDEX = "containsIndex";
  public static final String CONTAINS_INDEX_QUERYPARAM = "indexName";
  public static final String SHUTDOWN_INDEX = "shutdownIndex";
  public static final String SHUTDOWN_INDEX_QUERYPARAM = "indexName";
  public static final String SHUTDOWN_ALL = "shutdownAll";
//  public static final String FULL_INDEX_NAME_SET = "fullIndexNameSet";

  public static final String NOT_OK = "FALSE";
  public static final String OK = "TRUE";

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
  public static final String IDX_BASE_DIR = "_index";


  @Inject IndexStore indexStore; // here Singleton implicit

//  public String addDirectoryToScann(String path, boolean relative) {
//    return "";
//  }

  @GET()
  @Path(LIST_ALL_INDICES)
  @Produces(MediaType.APPLICATION_JSON)
  public String listAllIndices() {
    return new Gson().toJson(indexStore.getIndexNameSet());
  }

  @GET()
  @Path(REMOVE_INDEX)
  @Produces(MediaType.TEXT_PLAIN)
  public String removeIndex(@QueryParam(REMOVE_INDEX_QUERYPARAM) final String indexName) {
    return indexStore.removeIndex(indexName) ? OK : NOT_OK;
  }

  @GET()
  @Path(CONTAINS_INDEX)
  @Produces(MediaType.TEXT_PLAIN)
  public String containsIndex(@QueryParam(CONTAINS_INDEX_QUERYPARAM) final String indexName) {
    return indexStore.containsIndex(indexName) ? OK : NOT_OK;
  }

  @GET()
  @Path(SHUTDOWN_INDEX)
  @Produces(MediaType.TEXT_PLAIN)
  public String shutdownIndex(@QueryParam(SHUTDOWN_INDEX_QUERYPARAM) final String indexName) {
    indexStore.shutdownIndex(indexName);
    return OK;
  }

  @GET()
  @Path(SHUTDOWN_ALL)
  @Produces(MediaType.TEXT_PLAIN)
  public String shutdownAll() {
    indexStore.shutdownAll();
    return OK;
  }


//  @GET()
//  @Path(FULL_INDEX_NAME_SET)
//  @Produces(MediaType.APPLICATION_JSON)
//  public Set<String> getIndexNameSet() {
//    return Response
//        .ok(new Gson().toJson(indexStore.getIndexNameSet()))
//        .type(MediaType.APPLICATION_JSON)
//        .build();
//    return indexStore.getIndexNameSet();
//  }
}
