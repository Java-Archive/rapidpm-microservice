/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package junit.org.rapidpm.microservice.propertyservice.rest.v001;


import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;
import org.rapidpm.microservice.propertyservice.persistence.file.PropertiesFileLoader;
import org.rapidpm.microservice.propertyservice.rest.PropertyServiceRest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestTest001 extends BasicRestTest {

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    DI.activateDI(new PropertiesFileLoader());
    DI.registerClassForScope(PropertyServiceImpl.class, JVMSingletonInjectionScope.class.getSimpleName());
    System.setProperty("propertyservice.mapname", RestTest001.class.getSimpleName());
    System.setProperty("propertyservice.propertyfolder", this.getClass().getResource("").getPath());
  }

  @Override
  @AfterEach
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void test001() throws Exception {
    final String response = callLoadingOfProperties();
    Assertions.assertEquals("success", response);
  }

  private String callLoadingOfProperties() {
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/loadProperties?scope=rest01";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    client.close();
    return response;
  }

  @Test
  public void test002() throws Exception {
    callLoadingOfProperties();
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getSingleProperty?property=rest01.prop01";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    client.close();
    Assertions.assertEquals("test001", response);
  }

  @Test
  public void test003() throws Exception {
    callLoadingOfProperties();
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getIndexOfLoadedProperties";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    client.close();
    Assertions.assertNotNull(response);
    Assertions.assertFalse(response.isEmpty());
  }

  @Test
  public void test004() throws Exception {
    callLoadingOfProperties();
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getIndexOfScope?scope=rest01";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    client.close();
    Assertions.assertNotNull(response);
    Assertions.assertEquals("[\"rest01.prop01\"]", response);
  }

  @Test
  public void test005() throws Exception {
    callLoadingOfProperties();
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getPropertiesOfScope?scope=rest01";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    client.close();
    Assertions.assertNotNull(response);
    Assertions.assertEquals("{\"rest01.prop01\":\"test001\"}", response);
  }

}



