package junit.org.rapidpm.microservice.optionals.metrics.performance;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.optionals.metrics.performance.HistogrammSnapshot;
import org.rapidpm.microservice.optionals.metrics.performance.Histogramms;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by svenruppert on 15.12.15.
 */
public class HistogrammsTest extends BasicRestTest {


  public static final String HISTOGRAMM_NAME = TestRessource.class.getName() + ".doWork";

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    runRequests();


  }

  private void runRequests() throws Exception {

    final String generateBasicReqURL = generateBasicReqURL(TestRessource.class);
    final Client client = ClientBuilder.newClient();
    final WebTarget target = client.target(generateBasicReqURL);

    IntStream.range(0, 1_00)
        .forEach(i -> target.request().get(String.class));
    client.close();
  }

  @Test
  public void test001() throws Exception {

    final String result = requestWithCheck(generateBasicReqURL(Histogramms.class) + "/" + Histogramms.LIST_ALL_HISTOGRAMMS);
    final List<HistogrammSnapshot> histogrammSnapshots = new Gson().fromJson(result, Histogramms.HISTORY_SNAPSHOT_LIST_TYPE);
    Assert.assertTrue(histogrammSnapshots.isEmpty());

    DI.activateMetrics(TestRessource.class);
    runRequests();
    final String resultWithMetrics = requestWithCheck(generateBasicReqURL(Histogramms.class) + "/" + Histogramms.LIST_ALL_HISTOGRAMMS);
    Assert.assertNotNull(resultWithMetrics);
    final List<HistogrammSnapshot> histogrammSnapshotsWithMetrics = new Gson().fromJson(resultWithMetrics, Histogramms.HISTORY_SNAPSHOT_LIST_TYPE);
    Assert.assertNotNull(histogrammSnapshotsWithMetrics);
    Assert.assertFalse(histogrammSnapshotsWithMetrics.isEmpty());


    for (HistogrammSnapshot histogrammSnapshot : histogrammSnapshots) {
      Assert.assertNotNull(histogrammSnapshot);
    }
  }

  @NotNull
  private String requestWithCheck(final String generateBasicReqURL) {
    final String result = request(generateBasicReqURL);
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
    return result;
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
  public void test002() throws Exception {
    final String generateBasicReqURL = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.LIST_ONE_HISTOGRAMMS
        + "?" + Histogramms.QUERY_PARAM_HISTOGRAMM_NAME + "=" + HISTOGRAMM_NAME;

    fromJsonWithCheck(
        requestWithCheck(
            generateBasicReqURL));
  }

  @NotNull
  private HistogrammSnapshot fromJsonWithCheck(final String result) {
    final HistogrammSnapshot histogrammSnapshot = new Gson().fromJson(result, HistogrammSnapshot.class);
    Assert.assertNotNull(histogrammSnapshot);
    Assert.assertEquals(histogrammSnapshot.getName(), HISTOGRAMM_NAME);
    return histogrammSnapshot;
  }

  @Test
  public void test003() throws Exception {
    DI.activateMetrics(TestRessource.class);
    runRequests();

    final String generateBasicReqURL = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.LIST_ONE_HISTOGRAMMS
        + "?" + Histogramms.QUERY_PARAM_HISTOGRAMM_NAME + "=" + HISTOGRAMM_NAME;

    final HistogrammSnapshot preHistogrammSnapshot = fromJsonWithCheck(
        requestWithCheck(
            generateBasicReqURL));

    Assert.assertTrue(preHistogrammSnapshot.getHistogramCount() > 0);

    //DLETE
    final String generateBasicReqURL2Remove = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.REMOVE_ONE_HISTOGRAMMS
        + "?" + Histogramms.QUERY_PARAM_HISTOGRAMM_NAME + "=" + HISTOGRAMM_NAME;

    final HistogrammSnapshot histogrammSnapshot =
        fromJsonWithCheck(
            requestWithCheck(
                generateBasicReqURL2Remove));

    Assert.assertNotNull(histogrammSnapshot);
    Assert.assertTrue(histogrammSnapshot.getHistogramCount() > 0);

    final String checkURL = generateBasicReqURL(Histogramms.class)
        + "/" + Histogramms.LIST_ONE_HISTOGRAMMS
        + "?" + Histogramms.QUERY_PARAM_HISTOGRAMM_NAME + "=" + HISTOGRAMM_NAME;

    final HistogrammSnapshot check =
        fromJsonWithCheck(
            requestWithCheck(
                checkURL));

    Assert.assertEquals(check.getHistogramCount(), 0);

  }

  @Test
  public void test004() throws Exception {
    final String basicReqURL = generateBasicReqURL(Histogramms.class);
    final String generateBasicReqURL = basicReqURL + "/" + Histogramms.LIST_ALL_HISTOGRAMM_NAMES;
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final String requestWithCheck = requestWithCheck(generateBasicReqURL);
    System.out.println("requestWithCheck = " + requestWithCheck);
  }


}
