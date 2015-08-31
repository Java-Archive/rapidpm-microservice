package junit.org.rapidpm.microservice.rest.admin;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.rest.admin.BasicAdministration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * Created by svenruppert on 28.08.15.
 */
public class BasicAdminstrationTest extends BasicRestTest {

  @Override
  @After
  public void tearDown() throws Exception {
    //no stop
  }

  @Test
  public void test001() throws Exception {
    Client client = ClientBuilder.newClient();
    final String generateBasicReqURL = generateBasicReqURL(BasicAdministration.class);
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

//    Thread.currentThread().wait(10_000);

  }
}
