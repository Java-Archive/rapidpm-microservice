package junit.org.rapidpm.microservice.propertyservice;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.api.PropertyService;

import javax.inject.Inject;

public class BaseDITest {

  @Inject protected PropertyService propertyService;

  @BeforeEach
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages(this.getClass());
    DI.activatePackages("org.rapidpm");

    DI.activateDI(this);
    System.setProperty("mapname", this.getClass().getSimpleName());
    System.setProperty("file", this.getClass().getResource("").getPath());

    propertyService.init(this.getClass().getResource("example.properties").getPath());
    propertyService.loadProperties("example");
  }


  @AfterEach
  public void tearDown() throws Exception {

    if (propertyService != null) {
      propertyService.forget();
      propertyService.shutdown();
    }

    DI.clearReflectionModel();
  }
}
