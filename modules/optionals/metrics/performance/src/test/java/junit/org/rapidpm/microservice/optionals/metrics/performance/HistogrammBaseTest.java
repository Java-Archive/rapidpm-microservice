package junit.org.rapidpm.microservice.optionals.metrics.performance;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.rapidpm.microservice.optionals.metrics.performance.HistogrammSnapshot;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.stream.IntStream;

/**
 * Created by svenruppert on 12.01.16.
 */
public class HistogrammBaseTest extends BasicRestTest {


  public static final String HISTOGRAMM_NAME = TestRessource.class.getName() + ".doWork";

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    runRequests();
  }

  public int runRequests() throws Exception {
    final int endExclusive = 1_00;

    final String generateBasicReqURL = generateBasicReqURL(TestRessource.class);
    final Client client = ClientBuilder.newClient();
    final WebTarget target = client.target(generateBasicReqURL);

    IntStream.range(0, endExclusive)
        .forEach(i -> target.request().get(String.class));
    client.close();
    return endExclusive;
  }


  @NotNull
  public String requestWithCheck(final String generateBasicReqURL) {
    final String result = request(generateBasicReqURL);
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
    return result;
  }

  public String request(final String generateBasicReqURL) {
    Client client = ClientBuilder.newClient();
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    client.close();
    return val;
  }

  @NotNull
  public void requestWithCheckWithVoid(final String generateBasicReqURL) {
    Client client = ClientBuilder.newClient();
    client
        .target(generateBasicReqURL)
        .request();
    client.close();
  }

  @NotNull
  public HistogrammSnapshot fromJsonWithCheck(final String result) {
    final HistogrammSnapshot histogrammSnapshot = new Gson().fromJson(result, HistogrammSnapshot.class);
    Assert.assertNotNull(histogrammSnapshot);
    Assert.assertEquals(histogrammSnapshot.getName(), HISTOGRAMM_NAME);
    return histogrammSnapshot;
  }


}
