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

package junit.org.rapidpm.microservice.optionals.index.v001;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.optionals.index.IndexBasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import java.util.List;

public class IndexManagementTest extends IndexBasicRestTest {

  public static final String TESTINDEX = IndexManagementTest.class.getSimpleName();

  @Override
  public String getTestIndexName() {
    return TESTINDEX;
  }

  @Test
  public void listAllIndices() throws Exception {
    final String generateBasicReqURL = generateBasicReqURL(IndexManagement.class);
    final Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final Builder clientReq = client
        .target(generateBasicReqURL)
        .path(IndexManagement.LIST_ALL_INDICES)
        .request();
    final String response = clientReq.get(String.class);
    client.close();
    System.out.println("response = " + response);
    final List<String> fromJson = new Gson().fromJson(response, List.class);
    Assert.assertNotNull(fromJson);
    Assert.assertFalse(fromJson.isEmpty());
    Assert.assertEquals(1, fromJson.size());
    Assert.assertEquals(TESTINDEX, fromJson.get(0));
  }


}