package junit.org.rapidpm.microservice.demo;

import junit.org.rapidpm.microservice.BasicRestTest;
import junit.org.rapidpm.microservice.demo.rest.Resource;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by svenruppert on 07.07.15.
 */
public class RestTest extends BasicRestTest {


  @Test
  public void testApplicationPath() throws Exception {
//    server.deploy(JaxRsActivator.class);
    Client client = ClientBuilder.newClient();
    //MicroRestApp Path = /base
    //Resource Path = /test

//    final String restAppPath = Main.CONTEXT_PATH_REST;
//    final Path annotation = Resource.class.getAnnotation(Path.class);
//    final String ressourcePath = annotation.value();
//    final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
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
