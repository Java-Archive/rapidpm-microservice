package junit.org.rapidpm.microservice;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.test.PortUtils;
import org.rapidpm.microservice.test.RestUtils;

/**
 * Created by svenruppert on 28.08.15.
 */
public class BasicRestTest {

  final RestUtils restUtils = new RestUtils();

  @BeforeClass
  public static void setUpClass() {
    System.setProperty(Main.REST_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    System.setProperty(Main.SERVLET_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
  }

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
    setRestEasyPropertys(Main.DEFAULT_REST_PORT + "");
    try {
      Main.deploy();
    } catch (Exception e) {
      final StackTraceElement[] stackTrace = e.getStackTrace();
      for (StackTraceElement stackTraceElement : stackTrace) {
        System.out.println("stackTraceElement = " + stackTraceElement);
      }
    }
  }

  public void setRestEasyPropertys(final String port) {
    restUtils.setRestEasyPropertys(port);
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }

  public String generateBasicReqURL(Class restClass) {
    final String restAppPath = Main.CONTEXT_PATH_REST;
    return restUtils.generateBasicReqURL(restClass, restAppPath);
  }

  public void setRestEasyPropertys(final String host, final String port) {
    restUtils.setRestEasyPropertys(host, port);
  }
}
