package junit.org.rapidpm.microservice;

import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.Before;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.Path;

/**
 * Created by svenruppert on 28.08.15.
 */
public class BasicRestTest {

  @Before
  public void setUp() throws Exception {
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  public String generateBasicReqURL(Class restClass) {
    final String restAppPath = Main.CONTEXT_PATH_REST;
    return  new RestUtils().generateBasicReqURL(restClass, restAppPath);
  }

}
