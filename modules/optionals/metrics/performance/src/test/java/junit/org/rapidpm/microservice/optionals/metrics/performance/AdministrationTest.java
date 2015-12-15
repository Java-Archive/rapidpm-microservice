package junit.org.rapidpm.microservice.optionals.metrics.performance;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.metrics.performance.Administration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by svenruppert on 14.12.15.
 */
public class AdministrationTest extends BasicRestTest {

  @Test
  public void testStartJMXReporter() throws Exception {
    final String basicReqURL = generateBasicReqURL(Administration.class);
    final String start = request(basicReqURL + "/" + Administration.START_JMXREPORTER);
    Assert.assertNotNull(start);
    Assert.assertFalse(start.isEmpty());
    Assert.assertTrue(start.contains(Administration.START_JMXREPORTER));

    final String stop = request(basicReqURL + "/" + Administration.STOP_JMXREPORTER);
    Assert.assertNotNull(stop);
    Assert.assertFalse(stop.isEmpty());
    Assert.assertTrue(stop.contains(Administration.STOP_JMXREPORTER));
  }

  private String request(final String generateBasicReqURL) {
    Client client = ClientBuilder.newClient();
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    client.close();
    return val;
  }

  @Test
  public void testStartConsoleReporter() throws Exception {
    final String basicReqURL = generateBasicReqURL(Administration.class);
    final String start = request(basicReqURL + "/" + Administration.START_CONSOLEREPORTER);
    Assert.assertNotNull(start);
    Assert.assertFalse(start.isEmpty());
    Assert.assertTrue(start.contains(Administration.START_CONSOLEREPORTER));

    final String stop = request(basicReqURL + "/" + Administration.STOP_CONSOLEREPORTER);
    Assert.assertNotNull(stop);
    Assert.assertFalse(stop.isEmpty());
    Assert.assertTrue(stop.contains(Administration.STOP_CONSOLEREPORTER));

  }


}