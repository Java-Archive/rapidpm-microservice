package junit.org.rapidpm.microservice.propertyservice.impl;


import junit.org.rapidpm.microservice.propertyservice.BasicRestTest;
import junit.org.rapidpm.microservice.propertyservice.rest.Resource;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class BasicTest001 extends BasicRestTest {

  @Test @Ignore
  public void test001() throws Exception {
    Client client = ClientBuilder.newClient();
    final String generateBasicReqURL = generateBasicReqURL(Resource.class);
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    System.out.println("val = " + val);
    Assert.assertEquals("example", val);
    client.close();
  }

}



