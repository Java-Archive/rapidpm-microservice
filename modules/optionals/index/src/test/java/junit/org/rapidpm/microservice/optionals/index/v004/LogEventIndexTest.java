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

package junit.org.rapidpm.microservice.optionals.index.v004;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import junit.org.rapidpm.microservice.optionals.index.v004.api.LoggerEvent;
import junit.org.rapidpm.microservice.optionals.index.v004.server.LoggerEventIndex;
import junit.org.rapidpm.microservice.optionals.index.v004.server.LoggerEventREST;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import static junit.org.rapidpm.microservice.optionals.index.v004.server.LoggerEventREST.INDEX_NAME;

public class LogEventIndexTest extends BasicRestTest {

  @Inject IndexStore indexStore;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    DI.activateDI(this);

    final LoggerEventIndex loggerEventIndex = LoggerEventIndex.newBuilder()
        .withIndexName(INDEX_NAME)
        .build();
    indexStore.addIndexOfType(INDEX_NAME, loggerEventIndex);
    indexStore.removeIndex(INDEX_NAME);

  }

  @Test
  public void test001() throws Exception {

    final Gson gson = new Gson();
    final Encoder encoder = Base64.getEncoder();


    final LoggerEvent loggerEvent = new LoggerEvent().level("lev5").message("hello").timestamp(LocalDateTime.now());
    final String logeEventAsJson = gson.toJson(loggerEvent);
    final byte[] encodedI01 = encoder.encode(logeEventAsJson.getBytes());


    final Client client = ClientBuilder.newClient();
    final String generateBasicReqURL = generateBasicReqURL(LoggerEventREST.class);
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);

    final WebTarget webTarget = client
        .target(generateBasicReqURL + "/" + LoggerEventREST.QUERY)
        .queryParam(LoggerEventREST.QP_QUERY, "hello");

    final String q01 = webTarget.request().get(String.class);
    System.out.println("q01 = " + q01);

    final List<LogEvent> q01List = Arrays.asList(gson.fromJson(q01, LoggerEventREST.LOGGER_EVENT_LIST_TYPE));
    Assert.assertNotNull(q01List);
    Assert.assertTrue(q01List.isEmpty());

    final String i01 = client
        .target(generateBasicReqURL + "/" + LoggerEventREST.INSERT)
        .queryParam(LoggerEventREST.QP_LOGGER_EVENT, new String(encodedI01))
        .request()
        .get(String.class);
    System.out.println("i01 = " + i01);
    Assert.assertEquals(LoggerEventREST.OK, i01);

    final String q02 = webTarget
        .request()
        .get(String.class);
    System.out.println("q02 = " + q02);

    final List<LogEvent> q02List = Arrays.asList(gson.fromJson(q02, LoggerEventREST.LOGGER_EVENT_LIST_TYPE));
    Assert.assertNotNull(q02List);
    Assert.assertFalse(q02List.isEmpty());
    Assert.assertEquals(1, q02List.size());

    client.close();

  }
}
