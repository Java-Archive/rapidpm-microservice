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

package junit.org.rapidpm.microservice.optionals.index.v002;

import static org.junit.jupiter.api.Assertions.assertTrue;

import junit.org.rapidpm.microservice.optionals.index.IndexBasicRestTest;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;

public class IndexManagementRemoveTest extends IndexBasicRestTest {

  public static final String TESTINDEX = IndexManagementRemoveTest.class.getSimpleName();


  @Test
  public void removeIndex() throws Exception {
    final String generateBasicReqURL = generateBasicReqURL(IndexManagement.class);
    final Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final Builder clientReq = client
        .target(generateBasicReqURL)
        .path(IndexManagement.REMOVE_INDEX)
        .queryParam(IndexManagement.REMOVE_INDEX_QUERYPARAM, TESTINDEX)
        .request();
    final Boolean response = clientReq.get(Boolean.class);
    client.close();
    assertTrue(response);
  }

  @Override
  public String getTestIndexName() {
    return TESTINDEX;
  }
}