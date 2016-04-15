package junit.org.rapidpm.microservice.propertyservice.impl;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import java.util.Map;
import java.util.Set;

public class PropertyServiceTest002 {

  private PropertyServiceImpl service;

  @Before
  public void setUp() throws Exception {
    service = new PropertyServiceImpl();
    service.init();
  }

  @Test
  public void test001() throws Exception {
    final Set<String> index = service.getIndex();

    Assert.assertNotNull(index);
    Assert.assertTrue(index.size() > 0);
    Assert.assertTrue(index.contains("example.part01.001"));
  }

  @Test
  public void test002() throws Exception {
    final Set<String> indexToDomain = service.getIndexToDomain("single");

    Assert.assertNotNull(indexToDomain);
    Assert.assertEquals(1, indexToDomain.size());
    Assert.assertTrue(indexToDomain.contains("single.theonlykey"));
  }

  @Test
  public void test003() throws Exception {
    final Map<String, String> propertiesOfDomain = service.getPropertiesOfDomain("single");

    Assert.assertNotNull(propertiesOfDomain);
    Assert.assertEquals(1, propertiesOfDomain.size());
    Assert.assertTrue(propertiesOfDomain.keySet().contains("single.theonlykey"));
    Assert.assertEquals("theonlyvalue", propertiesOfDomain.get("single.theonlykey"));
  }
}
