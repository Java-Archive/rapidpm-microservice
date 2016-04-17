package junit.org.rapidpm.microservice.propertyservice.impl;


import junit.org.rapidpm.microservice.propertyservice.BaseDITest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.impl.PropertyService;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

public class PropertyServiceTest002 extends BaseDITest {

  @Inject
  PropertyService service;

  private static final String PROPERTY_KEY = "example.part01.001";
  private static final String PROPERTY_VALUE = "test001";

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    DI.activateDI(this);
    System.setProperty("file", this.getClass().getResource("example.properties").getPath());
    service.initFromCmd();
  }

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
    service.shutdown();
  }

  @Test
  public void test001() throws Exception {
    final String singleProperty = service.getSingleProperty(PROPERTY_KEY);

    Assert.assertNotNull(singleProperty);
    Assert.assertFalse(singleProperty.isEmpty());
    Assert.assertEquals(PROPERTY_VALUE, singleProperty);
  }

  @Test
  public void test002() throws Exception {
    final String singleProperty = service.getSingleProperty("example.invalid");
    Assert.assertTrue(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    final Set<String> index = service.getIndexOfLoadedProperties();

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
