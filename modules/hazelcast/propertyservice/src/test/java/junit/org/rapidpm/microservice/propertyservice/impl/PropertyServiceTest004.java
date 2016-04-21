package junit.org.rapidpm.microservice.propertyservice.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.propertyservice.api.PropertyService;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import java.lang.reflect.Method;

public class PropertyServiceTest004 {

  private PropertyService service;

  @Before
  public void setUp() throws Exception {
    service = new PropertyServiceImpl();
  }

  @Test
  public void test001() throws Exception {
    Method method = PropertyServiceImpl.class.getDeclaredMethod("getMapName");
    method.setAccessible(true);
    final String result = (String) method.invoke(service);
    Assert.assertEquals("properties", result);
  }

  @Test
  public void test002() throws Exception {
    System.setProperty("mapname", "PropertyServiceTest004");

    Method method = PropertyServiceImpl.class.getDeclaredMethod("getMapName");
    method.setAccessible(true);
    final String result = (String) method.invoke(service);
    Assert.assertEquals("PropertyServiceTest004", result);


  }
}
