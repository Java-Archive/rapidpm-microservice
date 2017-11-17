package junit.org.rapidpm.microservice.optionals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.optionals.ActiveUrlsDetector;
import org.rapidpm.microservice.optionals.ActiveUrlsHolder;
import org.rapidpm.microservice.rest.PingMe;
import junit.org.rapidpm.microservice.rest.provider.ProviderImpl;

public class ActiveUrlsDetectorTest {
  private ActiveUrlsDetector activeUrlsDetector = new ActiveUrlsDetector();

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    final Class<?> aClass = ProviderImpl.class;
    DI.activatePackages(aClass);
    DI.activatePackages(PingMe.class);
    DI.activatePackages(DemoResource.class);
  }

  @Test
  public void testDetectUrls() {
    ActiveUrlsHolder holder = activeUrlsDetector.detectUrls();

    assertThat(holder.getRestUrls().size(), is(2));
    assertThat(holder.getRestUrls().stream().filter(a -> a.endsWith("/rest/pingme")).count(),
        is(1L));
    assertThat(
        holder.getRestUrls().stream().filter(a -> a.endsWith("/rest/demo/sub - name")).count(),
        is(1L));
  }

  @Path("/demo")
  public static class DemoResource {
    @Path("sub")
    @GET
    public String test(@QueryParam("name") String name) {
      return "Hello " + name;
    }
  }
}
