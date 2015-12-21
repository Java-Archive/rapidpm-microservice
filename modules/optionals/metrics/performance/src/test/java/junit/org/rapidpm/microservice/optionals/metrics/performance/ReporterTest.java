package junit.org.rapidpm.microservice.optionals.metrics.performance;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.metrics.performance.Reporter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by svenruppert on 14.12.15.
 */
public class ReporterTest extends BasicRestTest {


  @Test
  public void testStartJMXReporter() throws Exception {
    final String basicReqURL = generateBasicReqURL(Reporter.class);
    final String start = request(basicReqURL + "/" + Reporter.START_JMXREPORTER);
    Assert.assertNotNull(start);
    Assert.assertFalse(start.isEmpty());
    Assert.assertTrue(start.contains(Reporter.START_JMXREPORTER));

    final String stop = request(basicReqURL + "/" + Reporter.STOP_JMXREPORTER);
    Assert.assertNotNull(stop);
    Assert.assertFalse(stop.isEmpty());
    Assert.assertTrue(stop.contains(Reporter.STOP_JMXREPORTER));
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
    final String basicReqURL = generateBasicReqURL(Reporter.class);
    final String start = request(basicReqURL + "/" + Reporter.START_CONSOLEREPORTER);
    Assert.assertNotNull(start);
    Assert.assertFalse(start.isEmpty());
    Assert.assertTrue(start.contains(Reporter.START_CONSOLEREPORTER));

    final String stop = request(basicReqURL + "/" + Reporter.STOP_CONSOLEREPORTER);
    Assert.assertNotNull(stop);
    Assert.assertFalse(stop.isEmpty());
    Assert.assertTrue(stop.contains(Reporter.STOP_CONSOLEREPORTER));

  }


}