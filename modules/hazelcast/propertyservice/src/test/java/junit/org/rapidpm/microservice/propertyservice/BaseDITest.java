package junit.org.rapidpm.microservice.propertyservice;


import org.junit.After;
import org.junit.Before;
import org.rapidpm.ddi.DI;

public class BaseDITest {

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages(this.getClass());
    DI.activatePackages("org.rapidpm");
  }


  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }
}
