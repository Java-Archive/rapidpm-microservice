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
package junit.org.rapidpm.microservice.optionals.metrics.health.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.optionals.metrics.health.rest.SessionHealth;
import org.rapidpm.microservice.optionals.metrics.health.rest.api.SessionHealthInfo;
import org.rapidpm.microservice.optionals.metrics.health.rest.api.SessionHealthInfoJsonConverter;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class SessionHealthTest extends BasicRestTest {

  private final String USER_AGENT = "Mozilla/5.0";



  @Test
  public void heathTest001() {
    final String generateBasicReqURL = generateBasicReqURL(SessionHealth.class);
    Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final Builder authcode = client
            .target(generateBasicReqURL)
            .request();
    final Response response = authcode.get();

    assertEquals(200, response.getStatus());
    String val = response.getStatusInfo().toString();
    System.out.println("response status info = " + val);
    client.close();

  }

  public String generateBasicReqURL(Class restClass) {
    final String restAppPath = MainUndertow.CONTEXT_PATH_REST;
    return new RestUtils().generateBasicReqURL(restClass, restAppPath);
  }

  @Test
  public void heathTest002() {
    final String generateBasicReqURL = generateBasicReqURL(SessionHealth.class);
    Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final Builder authcode = client
            .target(generateBasicReqURL)
            .request();
    String response = authcode.get(String.class);
    Gson gson = new Gson();
    SessionHealthInfo[] sessionHealthInfo = gson.fromJson(response, SessionHealthInfo[].class);

    assertEquals(0L, sessionHealthInfo[0].activeSessionCount);

    client.close();

  }

  @Test
  public void heathTest003() throws IOException {
    generateSession();
    generateSession();
    generateSession();

    String url = generateBasicReqURL(SessionHealth.class);
    URL obj = new URL(url);
    System.out.println("\nSending 'GET' request to URL : " + url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    // optional default is GET
    con.setRequestMethod("GET");
    //add request header
    con.setRequestProperty("User-Agent", USER_AGENT);

    int responseCode = con.getResponseCode();
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    final String jsonResponse = response.toString();
    assertFalse(jsonResponse.isEmpty());

    final List<SessionHealthInfo> healthInfos = new SessionHealthInfoJsonConverter().fromJsonList(jsonResponse);
    assertEquals(3L, healthInfos.get(0).activeSessionCount);
  }

  public void generateSession() throws IOException {

    String host = System.getProperty(MainUndertow.SERVLET_HOST_PROPERTY, "127.0.0.1");
    String port = System.getProperty(MainUndertow.SERVLET_PORT_PROPERTY);
    String microserviceContext = MainUndertow.MYAPP;


    String urlString = String.format("http://%s:%s%s/%s", host, port, microserviceContext, "test");
    URL testUrl = new URL(urlString);
    System.out.println("\nSending 'GET' request to URL : " + urlString);
    HttpURLConnection con = (HttpURLConnection) testUrl.openConnection();
    // optional default is GET
    con.setRequestMethod("GET");
    //add request header
    con.setRequestProperty("User-Agent", USER_AGENT);

    int responseCode = con.getResponseCode();
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
  }
}
