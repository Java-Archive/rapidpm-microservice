package junit.org.rapidpm.microservice.propertyservice.impl.v001;


import junit.org.rapidpm.microservice.propertyservice.BaseDITest;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.api.PropertyService;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Ignore
public class PropertyServiceTest001 extends BaseDITest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private static final String PROPERTY_KEY = "example.part01.001";
  private static final String PROPERTY_VALUE = "test001";


  @Test
  public void test001() throws Exception {
    final String singleProperty = propertyService.getSingleProperty(PROPERTY_KEY);

    Assert.assertNotNull(singleProperty);
    Assert.assertFalse(singleProperty.isEmpty());
    Assert.assertEquals(PROPERTY_VALUE, singleProperty);
  }

  @Test
  public void test002() throws Exception {
    final String singleProperty = propertyService.getSingleProperty("example.invalid");
    Assert.assertTrue(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    final Set<String> index = propertyService.getIndexOfLoadedProperties();
    System.out.println("index = " + index);
    Assert.assertNotNull(index);
    Assert.assertFalse(index.isEmpty());
    Assert.assertTrue(index.contains("example.part01.001"));
    Assert.assertTrue(index.contains("example.part01.002"));
  }

  @Test
  public void test004() throws Exception {
    final Set<String> indexToDomain = propertyService.getIndexOfScope("single");

    Assert.assertNotNull(indexToDomain);
    Assert.assertEquals(1, indexToDomain.size());
    Assert.assertTrue(indexToDomain.contains("single.theonlykey"));
  }

  @Test
  public void test005() throws Exception {
    final Map<String, String> propertiesOfDomain = propertyService.getPropertiesOfScope("single");

    Assert.assertNotNull(propertiesOfDomain);
    Assert.assertEquals(1, propertiesOfDomain.size());
    Assert.assertTrue(propertiesOfDomain.keySet().contains("single.theonlykey"));
    Assert.assertEquals("theonlyvalue", propertiesOfDomain.get("single.theonlykey"));
  }

}
