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

package junit.org.rapidpm.microservice.optionals.index.v005;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junit.org.rapidpm.microservice.BasicRestTest;
import junit.org.rapidpm.microservice.optionals.index.v005.rest.LibraryREST;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.optionals.index.stores.IndexStore;
import org.rapidpm.microservice.optionals.index.stores.indices.IndexOfTypeString;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import static junit.org.rapidpm.microservice.optionals.index.v005.rest.LibraryREST.INDEX_NAME;

public class LibraryIndexTest extends BasicRestTest {

  @Inject
  IndexStore indexStore;
  private Client client;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    DI.activateDI(this);

    final IndexOfTypeString libraryIndex = IndexOfTypeString.newBuilder()
        .withIndexName(INDEX_NAME)
        .build();
    indexStore.addIndexOfType(INDEX_NAME, libraryIndex);
    indexStore.removeIndex(INDEX_NAME);
  }

  @Test
  public void test001() throws Exception {
    insertText("lorem ipsum");
    insertText("such awesome text");
    insertText("ipsum lorem");
    insertText("mary had a little lamb");
    insertText("mary had a little lorem lorem lorem ipsum");

    WebTarget webTarget = generateTarget(LibraryREST.QUERY);
    String query01 = webTarget
        .queryParam(LibraryREST.QP_QUERY, "lorem")
        .request()
        .get(String.class);
    System.out.println("query01 = " + query01);

    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    final List<String> q02List = gson.fromJson(query01, LibraryREST.LIST_TYPE);
    Assert.assertNotNull(q02List);
    Assert.assertFalse(q02List.isEmpty());
    Assert.assertEquals(3, q02List.size());

    client.close();
  }

  private void insertText(String text) {
    String encodedText = encodeText(text);
    WebTarget webTarget = generateTarget(LibraryREST.INSERT);
    String insert01 = webTarget
        .queryParam(LibraryREST.QP_TEXT, new String(encodedText))
        .request()
        .get(String.class);
    Assert.assertEquals("OK", insert01);
  }


  private String encodeText(String text) {
    Gson gson = new Gson();
    Encoder encoder = Base64.getEncoder();
    String textAsJson = gson.toJson(text);
    return encoder.encodeToString(textAsJson.getBytes());
  }

  private WebTarget generateTarget(String path) {
    client = ClientBuilder.newClient();
    String generateBasicReqURL = generateBasicReqURL(LibraryREST.class);
    return client
        .target(generateBasicReqURL + "/" + path);
  }

}
