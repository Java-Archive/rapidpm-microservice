package junit.org.rapidpm.microservice.propertyservice.impl.v002;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.api.PropertyService;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

public class PropertyServiceTest002 /*extends BaseDITest*/ {


  private static final String PROPERTY_KEY = "example.part01.001";
  private static final String PROPERTY_VALUE = "test001";

  @Inject PropertyService service;

  @BeforeEach
  public void setUp() {

    System.setProperty("propertyservice.mapname", this.getClass().getSimpleName());
    System.setProperty("propertyservice.propertyfolder", PropertyServiceTest002.class.getResource("").getPath());

    DI.clearReflectionModel();
    DI.activatePackages(this.getClass());
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);

    service.initFromCmd();
    service.loadProperties("example");
  }

  @AfterEach
  public void tearDown() throws Exception {
    service.forget();
  }

  @Test
  public void test001() throws Exception {
    final String singleProperty = service.getSingleProperty(PROPERTY_KEY);
    Assertions.assertNotNull(singleProperty);
    Assertions.assertFalse(singleProperty.isEmpty());
    Assertions.assertEquals(PROPERTY_VALUE, singleProperty);
  }

  @Test
  public void test002() throws Exception {
    final String singleProperty = service.getSingleProperty("example.invalid");
    Assertions.assertTrue(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    final Set<String> index = service.getIndexOfLoadedProperties();

    Assertions.assertNotNull(index);
    Assertions.assertTrue(index.size() > 0);
    Assertions.assertTrue(index.contains("example.part01.001"));
    Assertions.assertTrue(index.contains("example.part01.002"));
  }

  @Test
  public void test004() throws Exception {
    final Set<String> indexToDomain = service.getIndexOfScope("single");

    Assertions.assertNotNull(indexToDomain);
    Assertions.assertEquals(1, indexToDomain.size());
    Assertions.assertTrue(indexToDomain.contains("single.theonlykey"));
  }

  @Test
  public void test005() throws Exception {
    final Map<String, String> propertiesOfDomain = service.getPropertiesOfScope("single");

    Assertions.assertNotNull(propertiesOfDomain);
    Assertions.assertEquals(1, propertiesOfDomain.size());
    Assertions.assertTrue(propertiesOfDomain.keySet().contains("single.theonlykey"));
    Assertions.assertEquals("theonlyvalue", propertiesOfDomain.get("single.theonlykey"));
  }
}
