package junit.org.rapidpm.microservice.demo;

import junit.org.rapidpm.microservice.BasicRestTest;
import junit.org.rapidpm.microservice.demo.rest.Resource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.test.PortUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by svenruppert on 07.07.15.
 */
public class RestTest extends BasicRestTest {

  @BeforeClass
  public static void setUpClass() {
    System.setProperty(Main.REST_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    System.setProperty(Main.SERVLET_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
  }

  @Test
  public void testApplicationPath() throws Exception {
    Client client = ClientBuilder.newClient();
    final String generateBasicReqURL = generateBasicReqURL(Resource.class);
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    System.out.println("val = " + val);
    Assert.assertEquals("Hello Rest World CDI Service", val);
    client.close();
  }


}
