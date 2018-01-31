package junit.org.rapidpm.microservice.propertyservice.persistence.file.v001;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.propertyservice.persistence.PropertiesLoader;
import org.rapidpm.microservice.propertyservice.persistence.file.PropertiesFileLoader;

import java.util.Map;

public class PropertiesFileLoaderTest001 {

  private PropertiesLoader fileLoader;

  @BeforeEach
  public void setUp() throws Exception {
    fileLoader = new PropertiesFileLoader();
  }

  @Test
  public void test001() throws Exception {
    final Map<String, String> properties = fileLoader.load(this.getClass().getResource("test.properties").getPath());

    Assertions.assertNotNull(properties);
    Assertions.assertTrue(properties.size() > 0);
    Assertions.assertTrue(properties.containsKey("domain01.property01"));
    Assertions.assertEquals("d01p01", properties.get("domain01.property01"));
  }
}
