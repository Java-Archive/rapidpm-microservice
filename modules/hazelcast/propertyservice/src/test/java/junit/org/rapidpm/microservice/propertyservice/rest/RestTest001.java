package junit.org.rapidpm.microservice.propertyservice.rest;


import junit.org.rapidpm.microservice.propertyservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.microservice.propertyservice.rest.PropertyServiceRest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class RestTest001 extends BasicRestTest {

  private PropertyServiceRest serviceRest;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    serviceRest = new PropertyServiceRest(this.getClass().getResource("example.properties").getPath());
  }


  @Test @Ignore
  public void test001() throws Exception {
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getSingleProperty?property=rest01.prop01";
    final Response response = client
        .target(uri)
        .request()
        .get();
    Assert.assertEquals(200, response.getStatus());
    client.close();

  }
}



