package junit.org.rapidpm.microservice.rest.optionals.admin; 

import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.rapidpm.microservice.rest.optionals.admin.BasicAdministration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/** 
* BasicAdministration Tester. 
* 
* @author <Authors name> 
* @since <pre>Dez 7, 2015</pre> 
* @version 1.0 
*/ 
public class BasicAdministrationTest extends BasicRestTest {

  @Override
  @After
  public void tearDown() throws Exception {
    //no stop and wait


  }

  @Test
  public void test001() throws Exception {
    final String generateBasicReqURL = generateBasicReqURL(BasicAdministration.class);
    Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final Invocation.Builder authcode = client
        .target(generateBasicReqURL)
        .path("authcode")
        .request();
    final Response response = authcode.get();

    Assert.assertEquals(200, response.getStatus());
    String val = response.getStatusInfo().toString();
    System.out.println("response status info = " + val);
    client.close();
    Thread.sleep(10_000);
  }

} 
