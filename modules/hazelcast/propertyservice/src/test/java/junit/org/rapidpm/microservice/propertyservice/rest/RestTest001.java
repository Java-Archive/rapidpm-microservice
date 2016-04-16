package junit.org.rapidpm.microservice.propertyservice.rest;


import junit.org.rapidpm.microservice.propertyservice.BaseRestTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.propertyservice.rest.PropertyServiceRest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestTest001 extends BaseRestTest {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    System.setProperty("file", this.getClass().getResource("example.properties").getPath());
  }

  @Test
  public void test001() throws Exception {
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/loadProperties?scope=example";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    //Assert.assertEquals(200, response.getStatus());
    client.close();
    Assert.assertEquals("success", response);
  }
}



