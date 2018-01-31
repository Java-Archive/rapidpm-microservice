package junit.org.rapidpm.microservice.propertyservice.persistence.file.v003;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.propertyservice.persistence.ConfigurationLoader;
import org.rapidpm.microservice.propertyservice.persistence.file.ConfigurationFileLoader;

import java.io.File;
import java.io.FileInputStream;


public class ConfigurationFileLoaderTest001 {

  private ConfigurationLoader configurationLoader;

  @BeforeEach
  public void setUp() throws Exception {
    final File file = new File(this.getClass().getResource("test.xml").getFile());
    System.setProperty("propertyservice.configfolder", file.getParentFile().getAbsolutePath());
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
