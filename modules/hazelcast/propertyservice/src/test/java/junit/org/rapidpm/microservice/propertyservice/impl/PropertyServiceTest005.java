package junit.org.rapidpm.microservice.propertyservice.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.api.PropertyService;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import java.util.Map;
import java.util.Set;

public class PropertyServiceTest005 {

  private static PropertyService service;

  @Before
  public void setUp() throws Exception {
    service = new PropertyServiceImpl();
    DI.activateDI(service);
    System.setProperty("mapname", this.getClass().getSimpleName());
    service.init(PropertyServiceTest005.class.getResource("example.properties").getPath());
  }

  @After
  public void tearDown() throws Exception {
    service.forget();
    service.shutdown();
  }

  @Test
  public void test001() throws Exception {
    final String singleProperty = service.getSingleProperty("example.part01.002");

    Assert.assertNotNull(singleProperty);
    Assert.assertFalse(singleProperty.isEmpty());
    Assert.assertEquals("test002", singleProperty);
  }

  @Test
  public void test002() throws Exception {
    final String singleProperty = service.getSingleProperty("example.invalid");
    Assert.assertTrue(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    final Set<String> index = service.getIndexOfLoadedProperties();
    System.out.println("index = " + index);
    Assert.assertNotNull(index);
    Assert.assertTrue(index.size() > 0);
    Assert.assertTrue(index.contains("example.part01.001"));
    Assert.assertTrue(index.contains("example.part01.002"));
  }

  @Test
  public void test004() throws Exception {
    final Set<String> indexToDomain = service.getIndexOfScope("single");

    Assert.assertNotNull(indexToDomain);
    Assert.assertEquals(1, indexToDomain.size());
    Assert.assertTrue(indexToDomain.contains("single.theonlykey"));
  }

  @Test
  public void test005() throws Exception {
    final Map<String, String> propertiesOfDomain = service.getPropertiesOfScope("single");

    Assert.assertNotNull(propertiesOfDomain);
    Assert.assertEquals(1, propertiesOfDomain.size());
    Assert.assertTrue(propertiesOfDomain.keySet().contains("single.theonlykey"));
    Assert.assertEquals("theonlyvalue", propertiesOfDomain.get("single.theonlykey"));
  }

}
