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

package junit.org.rapidpm.microservice.demo;

import junit.org.rapidpm.microservice.BasicRestTest;
import junit.org.rapidpm.microservice.demo.rest.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.rest.PingMe;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestTest extends BasicRestTest {

  @Test
  public void testApplicationPath() throws Exception {
    Client client = ClientBuilder.newClient();
    final String generateBasicReqURL = generateBasicReqURL(Resource.class);
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    System.out.println("val = " + val);
    Assertions.assertEquals("Hello Rest World CDI Service", val);
    client.close();
  }

  @Test
  public void testPingMe() throws Exception {
    Client client = ClientBuilder.newClient();
    final String generateBasicReqURL = generateBasicReqURL(PingMe.class);
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    System.out.println("val = " + val);
    Assertions.assertTrue(val.startsWith("Hello"));
    client.close();
  }





}
