package junit.org.rapidpm.microservice.optionals.header.v002;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.header.Header;
import org.rapidpm.microservice.optionals.header.HeaderInfo;

/**
 * Created by svenruppert on 04.11.15.
 */
public class HeaderTest {

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }



  @Test
  public void test001() throws Exception {
    Main.deploy();
  }

  @Header(order = 1)
  public static class HeaderDemo01 implements HeaderInfo {
    @Override
    public String createHeaderInfo() {
      return this.getClass().getSimpleName();
    }
  }

  @Header(order = 2)
  public static class HeaderDemo02 implements HeaderInfo {
    @Override
    public String createHeaderInfo() {
      return this.getClass().getSimpleName();
    }
  }

}
