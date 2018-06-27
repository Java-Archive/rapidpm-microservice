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
package junit.org.rapidpm.microservice.optionals.metrics.activeressources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.optionals.ActiveUrlsHolder;
import org.rapidpm.microservice.optionals.metrics.activeressources.ActiveUrls;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import java.util.List;

public class ActiveUrlsTest extends BasicRestTest {


  @Test
  public void test001() throws Exception {
    final Class<ActiveUrls> restClass = ActiveUrls.class;

    final String basicReqURL = generateBasicReqURL(restClass);
    System.out.println("basicReqURL = " + basicReqURL);

    Client client = ClientBuilder.newClient();
    final Builder authcode = client
        .target(basicReqURL)
        .request();
    final Response response = authcode.get();

    assertEquals(200, response.getStatus());
    String val = response.getStatusInfo().toString();
    final String data = response.readEntity(String.class);

    client.close();

    System.out.println("val = " + val);
    final ActiveUrlsHolder activeUrlsHolder = new Gson().fromJson(data, ActiveUrlsHolder.class);
    assertNotNull(activeUrlsHolder);
    final List<String> restUrls = activeUrlsHolder.getRestUrls();
    assertFalse(restUrls.isEmpty());

    final Path path = restClass.getAnnotation(Path.class);

    final boolean present = restUrls.stream()
        .anyMatch(s -> s.contains(path.value()));
    assertTrue(present);

    System.out.println("response status info = " + val);

    assertTrue(restUrls.stream()
        .filter(s -> s.contains(TestRessource.class.getAnnotation(Path.class).value()))
        .filter(s -> s.contains("pathA"))
        .anyMatch(s -> s.contains("paramA")));

    assertTrue(activeUrlsHolder.getServletCounter() > 0);

    assertTrue(activeUrlsHolder.getServletUrls().stream()
        .filter(s -> s.contains(TestServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0]))
        .anyMatch(s -> s.contains("testServlet")));


  }


  @Path("/OverviewTest")
  public static class TestRessource {
    @GET()
    @Path("pathA")
    public void doWork(@QueryParam("paramA") String param) {
    }
  }

  @WebServlet(urlPatterns = "testServlet")
  public static class TestServlet extends HttpServlet {

  }



}
