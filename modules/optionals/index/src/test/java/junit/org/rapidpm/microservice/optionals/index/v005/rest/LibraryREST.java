package junit.org.rapidpm.microservice.optionals.index.v005.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junit.org.rapidpm.microservice.optionals.index.v004.api.IndexOfTypeLoggerEvent;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;
import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfTypeString;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;

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
 * Created by RapidPM - Team on 01.12.2016.
 */
@Path("/index/library")
public class LibraryREST {

  public static final String INDEX_NAME = IndexOfTypeString.class.getSimpleName();
  public static final String QUERY = "query";
  public static final String QP_QUERY = "query";
  public static final String QP_TEXT = "text";
  public static final String INSERT = "insert";
  public static final String OK = "OK";
  public static final Type LIST_TYPE = List.class;


  @Inject
  IndexStore indexStore;

  @GET()
  @Path(QUERY)
  @Produces(MediaType.APPLICATION_JSON)
  public String query(@QueryParam(QP_QUERY) String query) {
    final IndexOfTypeString index = indexStore.getIndex(INDEX_NAME);
    final List<String> texts = index.query(query);
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    return gson.toJson(texts);
  }

  @GET()
  @Path(INSERT)
  @Produces(MediaType.APPLICATION_JSON)
  public String insert(@QueryParam(QP_TEXT) final String encodedText) {
    final Gson gson = new Gson();
    final Base64.Decoder decoder = Base64.getDecoder();

    final String text = gson.fromJson(new String(decoder.decode(encodedText)), (Type) String.class);
    final IndexOfTypeString index = indexStore.getIndex(INDEX_NAME);
    index.addElement(text);
    return OK;
  }

  @PostConstruct
  public void postConstruct() {
    if (indexStore.containsIndex(INDEX_NAME)) {
      // all fine....
    } else {
      final IndexOfTypeString libraryIndex = IndexOfTypeString.newBuilder()
          .withIndexName(INDEX_NAME)
          .build();
      indexStore.addIndexOfType(INDEX_NAME, libraryIndex);
    }
  }


}
