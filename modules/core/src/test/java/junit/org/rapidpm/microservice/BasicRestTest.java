package junit.org.rapidpm.microservice;

import org.junit.After;
import org.junit.Before;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.test.RestUtils;

/**
 * Created by svenruppert on 28.08.15.
 */
public class BasicRestTest {

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }

  public String generateBasicReqURL(Class restClass) {
    final String restAppPath = Main.CONTEXT_PATH_REST;
    return new RestUtils().generateBasicReqURL(restClass, restAppPath);
  }

}
