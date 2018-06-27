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
package junit.org.rapidpm.microservice.optionals.metrics.jvm.v001;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.optionals.metrics.jvm.JVMRessources;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.GCInfos;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.MemoryInfos;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.OSInfos;
import org.rapidpm.microservice.optionals.metrics.jvm.model.jvm.SpecInfos;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class JVMRessourcesTest extends BasicRestTest {
  @Test
  public void testOSInfos() throws Exception {

    final String data = makeRequestForValue("osinfos");
    assertNotNull(data);
    assertFalse(data.isEmpty());
    final OSInfos osInfos = new Gson().fromJson(data, OSInfos.class);
    assertNotNull(osInfos);
    System.out.println("osInfos = " + osInfos);
  }

  private String makeRequestForValue(final String methodPath) {
    final Class<JVMRessources> restClass = JVMRessources.class;
    final String basicReqURL = generateBasicReqURL(restClass) + "/" + methodPath;
    System.out.println("basicReqURL = " + basicReqURL);

    final Client client = ClientBuilder.newClient();
    final Builder authcode = client
        .target(basicReqURL)
        .request();
    final Response response = authcode.get();

    assertEquals(200, response.getStatus());
    String val = response.getStatusInfo().toString();
    System.out.println("val = " + val);
    assertNotNull(val);
    assertEquals("OK", val);
    final String data = response.readEntity(String.class);
    System.out.println("data = " + data);
    client.close();
    return data;
  }

  @Test
  public void testLoadInfos() throws Exception {

    final String data = makeRequestForValue("loadinfos");
    assertNotNull(data);
    assertFalse(data.isEmpty());

//    final Type listType = new TypeToken<ArrayList<GCInfos>>() {
//    }.getType();
    final List<GCInfos> gcInfosList = Arrays.asList(new Gson().fromJson(data, GCInfos[].class));

    assertNotNull(gcInfosList);
    assertFalse(gcInfosList.isEmpty());
    System.out.println("loadinfos = " + gcInfosList);
  }

  @Test
  public void testSpecInfos() throws Exception {

    final String data = makeRequestForValue("specinfos");
    assertNotNull(data);
    assertFalse(data.isEmpty());

    final SpecInfos specInfos = new Gson().fromJson(data, SpecInfos.class);

    assertNotNull(specInfos);
    System.out.println("specInfos = " + specInfos);
  }

  @Test
  public void testMemoryInfos() throws Exception {

    final String data = makeRequestForValue("memoryinfos");
    assertNotNull(data);
    assertFalse(data.isEmpty());

    final MemoryInfos memoryInfos = new Gson().fromJson(data, MemoryInfos.class);

    assertNotNull(memoryInfos);
    System.out.println("memoryinfos = " + memoryInfos);
  }

}
