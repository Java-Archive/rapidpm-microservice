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

package junit.org.rapidpm.microservice.optionals.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import static org.rapidpm.microservice.optionals.cli.DefaultCmdLineOptions.CMD_REST_PORT;

public class MainTest004 extends BaseCmdlineTest{


  public static final String GOT_IT = "Got it .. ";
  public static final int PORT = new PortUtils().nextFreePortForTest();

  @Test
  public void test001() throws Exception {
    Main.main(new String[]{"-" + CMD_REST_PORT + " " + PORT});

    final String restAppPath = MainUndertow.CONTEXT_PATH_REST;
    final String generateBasicReqURL = new RestUtils().generateBasicReqURL(PortDemoRest.class, restAppPath);

    Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final String result = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    Assertions.assertEquals(GOT_IT, result);
    Assertions.assertTrue(generateBasicReqURL.contains(PORT + ""));
    client.close();
  }


  @AfterEach
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Path("/portdemo")
  public static class PortDemoRest {
    @GET()
    @Produces("text/plain")
    public String get() {
      return GOT_IT;
    }

  }


}
