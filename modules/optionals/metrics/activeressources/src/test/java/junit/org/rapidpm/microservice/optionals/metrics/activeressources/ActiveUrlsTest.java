package junit.org.rapidpm.microservice.optionals.metrics.activeressources;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.header.ActiveUrlsHolder;
import org.rapidpm.microservice.optionals.metrics.activeressources.ActiveUrls;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by svenruppert on 08.12.15.
 */
public class ActiveUrlsTest extends BasicRestTest {


  @Test
  public void test001() throws Exception {
    final Class<ActiveUrls> restClass = ActiveUrls.class;

    final String basicReqURL = generateBasicReqURL(restClass);
    System.out.println("basicReqURL = " + basicReqURL);

    Client client = ClientBuilder.newClient();
    final Invocation.Builder authcode = client
        .target(basicReqURL)
        .request();
    final Response response = authcode.get();

    Assert.assertEquals(200, response.getStatus());
    String val = response.getStatusInfo().toString();
    final String data = response.readEntity(String.class);

    client.close();

    System.out.println("val = " + val);
    final ActiveUrlsHolder activeUrlsHolder = new Gson().fromJson(data, ActiveUrlsHolder.class);
    Assert.assertNotNull(activeUrlsHolder);
    final List<String> restUrls = activeUrlsHolder.getRestUrls();
    Assert.assertFalse(restUrls.isEmpty());

    final Path path = restClass.getAnnotation(Path.class);

    final boolean present = restUrls.stream()
        .filter(s -> s.contains(path.value()))
        .findFirst()
        .isPresent();
    Assert.assertTrue(present);

    System.out.println("response status info = " + val);

    Assert.assertTrue(restUrls.stream()
        .filter(s -> s.contains(TestRessource.class.getAnnotation(Path.class).value()))
        .filter(s -> s.contains("pathA"))
        .filter(s -> s.contains("paramA"))
        .findFirst()
        .isPresent());

    Assert.assertTrue(activeUrlsHolder.getServletCounter() > 0);

    Assert.assertTrue(activeUrlsHolder.getServletUrls().stream()
        .filter(s -> s.contains(TestServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0]))
        .filter(s -> s.contains("testServlet"))
        .findFirst()
        .isPresent());


  }


  @Path("/OverviewTest")
  public static class TestRessource {
    @GET()
    @Path("pathA")
    public void doWork(@QueryParam("paramA") String param) {
    }
  }

  @WebServlet(urlPatterns = "testServlet")
  public static class TestServlet extends HttpServlet {

  }



}