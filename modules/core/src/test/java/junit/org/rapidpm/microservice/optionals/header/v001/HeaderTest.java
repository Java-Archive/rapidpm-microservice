package junit.org.rapidpm.microservice.optionals.header.v001;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;

/**
 * Created by svenruppert on 04.11.15.
 */
public class HeaderTest {

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass().getPackage().getName());
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }


  @Test
  public void test001() throws Exception {


  }

}
