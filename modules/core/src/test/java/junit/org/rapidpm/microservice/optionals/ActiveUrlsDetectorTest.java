package junit.org.rapidpm.microservice.optionals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.optionals.ActiveUrlsDetector;
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
    DI.activatePackages(HelloResource.class);
  }

  @Test
  public void testServletCount() {
    assertThat(activeUrlsDetector.detectUrls().getServletCounter(), is(2L));
  }

  @Test
  public void testDetectUrlsServlet() {
    List<String> servletURls = activeUrlsDetector.detectUrls().getServletUrls();

    assertThat(servletURls.size(), is(2));
    assertThat(servletURls.stream().filter(a -> a.endsWith("/microservice/demoServlet")).count(),
        is(1L));
  }

  @Test
  public void testDetectUrlsRest() {
    List<String> restUrls = activeUrlsDetector.detectUrls().getRestUrls();
    assertThat(restUrls.size(), is(3));
    assertThat(restUrls.stream().filter(a -> a.endsWith("/rest/pingme")).count(), is(1L));
    assertThat(restUrls.stream().filter(a -> a.endsWith("/rest/demo/sub - name")).count(), is(1L));
    assertThat(restUrls.stream().filter(a -> a.endsWith("/rest/interface")).count(), is(1L));
  }

  @Path("/interface")
  public static interface HelloResource {
    @GET
    public String hello();
  }

  public static class HelloResourceImpl implements HelloResource {
    @Override
    public String hello() {
      return "Hello, World!";
    }
  }

  @Path("/demo")
  public static class DemoResource {
    @Path("sub")
    @GET
    public String test(@QueryParam("name") String name) {
      return "Hello " + name;
    }
  }

  @WebServlet(urlPatterns = "/demoServlet")
  public static class DemoServlet extends HttpServlet {
  }

  @WebServlet(urlPatterns = "/demoServlet2")
  public static class DemoServlet2 extends HttpServlet {
  }
  @WebServlet
  public static class DemoServletWithEmptyUrlPattern extends HttpServlet {
  }
}
