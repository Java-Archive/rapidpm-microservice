package junit.org.rapidpm.microservice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.rest.admin.BasicAdministration;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import static org.rapidpm.microservice.optionals.cli.DefaultCmdLineOptions.CMD_REST_PORT;

/**
 * Created by svenruppert on 14.09.15.
 */
public class MainTest004 {


  public static final String GOT_IT = "Got it .. ";

  @Test
  public void test001() throws Exception {
    Main.main(new String[]{"-"+ CMD_REST_PORT + " 1234"});

    final String restAppPath = Main.CONTEXT_PATH_REST;
    final String generateBasicReqURL = new RestUtils().generateBasicReqURL(PortDemoRest.class, restAppPath);

    Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final String result = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    Assert.assertEquals(GOT_IT, result);

    client.close();
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Path("/portdemo")
  public static class PortDemoRest{
    @GET()
    @Produces("text/plain")
    public String get() {
      return GOT_IT;
    }

  }



}
