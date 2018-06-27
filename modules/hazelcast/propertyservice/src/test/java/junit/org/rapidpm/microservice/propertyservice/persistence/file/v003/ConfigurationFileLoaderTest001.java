/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
