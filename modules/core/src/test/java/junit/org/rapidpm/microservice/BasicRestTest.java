package junit.org.rapidpm.microservice;

import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.Before;
import org.rapidpm.microservice.Main;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;

/**
 * Created by svenruppert on 28.08.15.
 */
public class BasicRestTest {

  @Before
  public void setUp() throws Exception {
    System.setProperty(Main.RESTEASY_PORT, Main.PORT_REST + "");
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  public String generateBasicReqURL(Class restClass) {
    final String restAppPath = Main.CONTEXT_PATH_REST;

    if (restClass.isAnnotationPresent(Path.class)) {
      final Path path = (Path) restClass.getAnnotation(Path.class);
      final String ressourcePath = path.value();
      final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
      return generateURL;
    }
    throw new RuntimeException("Class without Path Annotation " + restClass);
  }

}
