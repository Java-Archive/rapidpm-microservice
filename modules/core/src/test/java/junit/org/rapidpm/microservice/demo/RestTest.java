package junit.org.rapidpm.microservice.demo;

import junit.org.rapidpm.microservice.demo.rest.Resource;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by svenruppert on 07.07.15.
 */
public class RestTest {

  @Before
  public void setUp() throws Exception {
    System.setProperty(Main.RESTEASY_PORT, Main.PORT_REST+"");
    Main.deploy();
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testApplicationPath() throws Exception {
//    server.deploy(JaxRsActivator.class);
    Client client = ClientBuilder.newClient();
    //MicroRestApp Path = /base
    //Resource Path = /test

    final String restAppPath = Main.CONTEXT_PATH_REST;
    final String ressourcePath = Resource.class.getAnnotation(Path.class).value();
    final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
    System.out.println("generateURL = " + generateURL);
    String val = client
        .target(generateURL)
        .request()
        .get(String.class);
    System.out.println("val = " + val);
    Assert.assertEquals("Hello Rest World CDI Service", val);
    client.close();
  }



}
