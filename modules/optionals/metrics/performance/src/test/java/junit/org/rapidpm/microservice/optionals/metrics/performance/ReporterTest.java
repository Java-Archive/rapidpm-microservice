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

package junit.org.rapidpm.microservice.optionals.metrics.performance;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.metrics.performance.Reporter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ReporterTest extends BasicRestTest {


  @Test
  public void testStartJMXReporter() throws Exception {
    final String basicReqURL = generateBasicReqURL(Reporter.class);
    final String start = request(basicReqURL + "/" + Reporter.START_JMXREPORTER);
    Assert.assertNotNull(start);
    Assert.assertFalse(start.isEmpty());
    Assert.assertTrue(start.contains(Reporter.START_JMXREPORTER));

    final String stop = request(basicReqURL + "/" + Reporter.STOP_JMXREPORTER);
    Assert.assertNotNull(stop);
    Assert.assertFalse(stop.isEmpty());
    Assert.assertTrue(stop.contains(Reporter.STOP_JMXREPORTER));
  }

  private String request(final String generateBasicReqURL) {
    Client client = ClientBuilder.newClient();
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    client.close();
    return val;
  }

  @Test
  public void testStartConsoleReporter() throws Exception {
    final String basicReqURL = generateBasicReqURL(Reporter.class);
    final String start = request(basicReqURL + "/" + Reporter.START_CONSOLEREPORTER);
    Assert.assertNotNull(start);
    Assert.assertFalse(start.isEmpty());
    Assert.assertTrue(start.contains(Reporter.START_CONSOLEREPORTER));

    final String stop = request(basicReqURL + "/" + Reporter.STOP_CONSOLEREPORTER);
    Assert.assertNotNull(stop);
    Assert.assertFalse(stop.isEmpty());
    Assert.assertTrue(stop.contains(Reporter.STOP_CONSOLEREPORTER));

  }


}