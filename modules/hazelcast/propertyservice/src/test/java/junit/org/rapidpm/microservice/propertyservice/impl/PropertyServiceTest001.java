package junit.org.rapidpm.microservice.propertyservice.impl;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

public class PropertyServiceTest001 {

  private PropertyServiceImpl service;

  @Before
  public void setUp() throws Exception {
    service = new PropertyServiceImpl();
    service.init();
  }

  @Test
  public void test001() throws Exception {
    final String singleProperty = service.getSingleProperty("example.part01.001");

    Assert.assertNotNull(singleProperty);
    Assert.assertEquals("test001", singleProperty);
  }

  @Test
  public void test002() throws Exception {
    final String singleProperty = service.getSingleProperty("example.invalid");
    Assert.assertTrue(singleProperty.isEmpty());
  }
}
