package junit.org.rapidpm.microservice.propertyservice.impl.v001;


import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import junit.org.rapidpm.microservice.propertyservice.BaseDITest;

@Disabled
public class PropertyServiceTest001 extends BaseDITest {

  //  @Rule
//  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private static final String PROPERTY_KEY   = "example.part01.001";
  private static final String PROPERTY_VALUE = "test001";


  @Test
  public void test001() throws Exception {
    final String singleProperty = propertyService.getSingleProperty(PROPERTY_KEY);

    Assertions.assertNotNull(singleProperty);
    Assertions.assertFalse(singleProperty.isEmpty());
    Assertions.assertEquals(PROPERTY_VALUE , singleProperty);
  }

  @Test
  public void test002() throws Exception {
    final String singleProperty = propertyService.getSingleProperty("example.invalid");
    Assertions.assertTrue(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    final Set<String> index = propertyService.getIndexOfLoadedProperties();
    System.out.println("index = " + index);
    Assertions.assertNotNull(index);
    Assertions.assertFalse(index.isEmpty());
    Assertions.assertTrue(index.contains("example.part01.001"));
    Assertions.assertTrue(index.contains("example.part01.002"));
  }

  @Test
  public void test004() throws Exception {
    final Set<String> indexToDomain = propertyService.getIndexOfScope("single");

    Assertions.assertNotNull(indexToDomain);
    Assertions.assertEquals(1 , indexToDomain.size());
    Assertions.assertTrue(indexToDomain.contains("single.theonlykey"));
  }

  @Test
  public void test005() throws Exception {
    final Map<String, String> propertiesOfDomain = propertyService.getPropertiesOfScope("single");

    Assertions.assertNotNull(propertiesOfDomain);
    Assertions.assertEquals(1 , propertiesOfDomain.size());
    Assertions.assertTrue(propertiesOfDomain.keySet().contains("single.theonlykey"));
    Assertions.assertEquals("theonlyvalue" , propertiesOfDomain.get("single.theonlykey"));
  }

}
