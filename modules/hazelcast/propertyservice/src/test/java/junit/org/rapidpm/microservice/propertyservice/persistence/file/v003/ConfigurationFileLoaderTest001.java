package junit.org.rapidpm.microservice.propertyservice.persistence.file.v003;


import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.propertyservice.persistence.ConfigurationLoader;
import org.rapidpm.microservice.propertyservice.persistence.file.ConfigurationFileLoader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConfigurationFileLoaderTest001 {

  private ConfigurationLoader configurationLoader;

  @Before
  public void setUp() throws Exception {
    System.setProperty("basepath", this.getClass().getResource("").getPath().substring(1));
    configurationLoader = new ConfigurationFileLoader();
  }

  @Test
  public void test001() throws Exception {
    final File file = configurationLoader.loadConfigurationFile("test.xml");
    assertNotNull(file);
  }

  @Test
  public void test002() throws Exception {
    final File resourceFile = new File(this.getClass().getResource("test.xml").getFile());
    final File loaderFile = configurationLoader.loadConfigurationFile("test.xml");
    assertNotNull(resourceFile);
    assertNotNull(loaderFile);

    long resourceSize = new FileInputStream(resourceFile).getChannel().size();
    long loaderSize = new FileInputStream(loaderFile).getChannel().size();

    assertEquals(resourceSize, loaderSize);
  }
}
