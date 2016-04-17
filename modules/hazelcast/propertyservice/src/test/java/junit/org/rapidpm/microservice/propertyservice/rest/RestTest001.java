package junit.org.rapidpm.microservice.propertyservice.rest;


import junit.org.rapidpm.microservice.propertyservice.BaseRestTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.impl.PropertyService;
import org.rapidpm.microservice.propertyservice.persistence.file.PropertiesFileLoader;
import org.rapidpm.microservice.propertyservice.rest.PropertyServiceRest;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Set;

@Ignore
public class RestTest001 extends BaseRestTest {

  @Inject
  PropertyService propertyService;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    DI.activateDI(new PropertiesFileLoader());
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

  @Test
  public void test002() throws Exception {
    test001();
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getSingleProperty?property=rest01.prop01";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    client.close();
    Assert.assertEquals("test001", response);
  }

  @Test
  public void test003() throws Exception {
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getIndexOfLoadedProperties";
    final Set<String> response = client
        .target(uri)
        .request()
        .get(Set.class);
    //Assert.assertEquals(200, response.getStatus());
    client.close();
    Assert.assertNotNull(response);
    Assert.assertTrue(response.size() == 1);
  }

  @Test
  public void test004() throws Exception {
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getIndexOfScope?scope=rest01";
    final Set<String> response = client
        .target(uri)
        .request()
        .get(Set.class);
    client.close();
    Assert.assertNotNull(response);
    Assert.assertTrue(response.size() == 1);
  }

  @Test
  public void test005() throws Exception {
    Client client = ClientBuilder.newClient();
    final String uri = generateBasicReqURL(PropertyServiceRest.class) + "/getPropertiesOfScope?scope=rest01";
    final String response = client
        .target(uri)
        .request()
        .get(String.class);
    client.close();
    Assert.assertNotNull(response);
    Assert.assertTrue(response.length() == 1);
  }

}



