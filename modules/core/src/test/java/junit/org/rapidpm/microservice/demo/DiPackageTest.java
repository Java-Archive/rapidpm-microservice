package junit.org.rapidpm.microservice.demo;

import junit.org.rapidpm.microservice.BasicRestTest;
import junit.org.rapidpm.microservice.demo.rest.Resource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.ddi.DI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import static org.junit.Assert.assertEquals;

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
 * Created by m.lang - RapidPM - Team on 25.04.2017.
 */
public class DiPackageTest extends BasicRestTest {

  @BeforeClass
  public static void beforeClass(){
    System.setProperty(DI.ORG_RAPIDPM_DDI_PACKAGESFILE, "junit/org/rapidpm/microservice/microservice.packages");
  }

  @AfterClass
  public static void afterClass(){
    System.clearProperty(DI.ORG_RAPIDPM_DDI_PACKAGESFILE);
  }


  @Test
  public void test001() throws Exception {
    assertEquals("Hello Rest World CDI Service", callRestEndpoint());
  }

  private String callRestEndpoint() {
    Client client = ClientBuilder.newClient();
    String generateBasicReqURL = generateBasicReqURL(Resource.class);
    String result = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    client.close();
    return result;
  }


}
