package junit.org.rapidpm.microservice.rest.info;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.BasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.header.ActiveUrlsHolder;
import org.rapidpm.microservice.rest.info.ActiveUrls;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by svenruppert on 23.11.15.
 */
public class ActiveUrlsTest extends BasicRestTest {


  @Test
  public void test001() throws Exception {
    final String basicReqURL = generateBasicReqURL(ActiveUrls.class);
    System.out.println("basicReqURL = " + basicReqURL);

    Client client = ClientBuilder.newClient();
    final Invocation.Builder authcode = client
        .target(basicReqURL)
        .request();
    final Response response = authcode.get();

    Assert.assertEquals(200, response.getStatus());
    String val = response.getStatusInfo().toString();
    final String data = response.readEntity(String.class);

    System.out.println("val = " + val);
    final ActiveUrlsHolder activeUrlsHolder = new Gson().fromJson(data, ActiveUrlsHolder.class);
    Assert.assertNotNull(activeUrlsHolder);
    final List<String> restUrls = activeUrlsHolder.getRestUrls();
    Assert.assertFalse(restUrls.isEmpty());

    final Path path = ActiveUrls.class.getAnnotation(Path.class);

    final boolean present = restUrls.stream()
        .filter(s -> s.contains(path.value()))
        .findFirst()
        .isPresent();
    Assert.assertTrue(present);

    System.out.println("response status info = " + val);
    client.close();
  }
}
