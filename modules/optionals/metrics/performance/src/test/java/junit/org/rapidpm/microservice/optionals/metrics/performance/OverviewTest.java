package junit.org.rapidpm.microservice.optionals.metrics.performance;

import com.codahale.metrics.Histogram;
import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.metrics.performance.HistogrammSnapshot;
import org.rapidpm.microservice.optionals.metrics.performance.Overview;
import org.rapidpm.proxybuilder.core.metrics.RapidPMMetricsRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by svenruppert on 15.12.15.
 */
public class OverviewTest extends BasicRestTest {


  @Test
  public void test001() throws Exception {

    //make testCalls
    runRequests();

    final String basicReqURL = generateBasicReqURL(Overview.class);
    final String result = request(basicReqURL + "/" + Overview.LIST_ALL_HISTOGRAMMS);
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());

    System.out.println("result = " + result);
    final List<HistogrammSnapshot> histogrammSnapshots = new Gson().fromJson(result, Overview.HISTORY_SNAPSHOT_LIST_TYPE);
    Assert.assertFalse(histogrammSnapshots.isEmpty());

    for (HistogrammSnapshot histogrammSnapshot : histogrammSnapshots) {
      Assert.assertNotNull(histogrammSnapshot);
    }
  }

  public void runRequests() throws Exception {

    final String generateBasicReqURL = generateBasicReqURL(TestRessource.class);
    final Client client = ClientBuilder.newClient();
    final WebTarget target = client.target(generateBasicReqURL);

    IntStream.range(0, 1_000)
        .forEach(i -> {
          final String result = target.request().get(String.class);
        });
    client.close();
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

  @Path("/OverviewTest")
//  @StaticMetricsProxy
  public static class TestRessource {
    @GET()
    public String doWork() {
      final Histogram histogram = RapidPMMetricsRegistry.getInstance().getMetrics().histogram(TestRessource.class.getSimpleName());
      final LocalDateTime now = LocalDateTime.now();
      final String format = now.format(DateTimeFormatter.ISO_DATE_TIME);
      final LocalDateTime stop = LocalDateTime.now();

      histogram.update((stop.getNano() - now.getNano()));

      return format;
    }
  }

}
