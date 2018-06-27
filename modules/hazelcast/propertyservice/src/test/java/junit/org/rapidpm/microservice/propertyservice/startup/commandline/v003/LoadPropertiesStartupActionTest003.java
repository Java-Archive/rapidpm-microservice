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
package junit.org.rapidpm.microservice.propertyservice.startup.commandline.v003;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;

import java.util.Optional;
import java.util.Properties;


public class LoadPropertiesStartupActionTest003 {

  //@Rule
  //public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void test001() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());

    //final File file = folder.newFile("test.properties");
    //writePropertiesToFile(file);
    //final Optional<String[]> args = Optional.of(new String[]{"-i" + file.getAbsoluteFile().getCanonicalPath()});
    final Optional<String[]> args = Optional.of(new String[]{"-i" + this.getClass().getResource("test.properties").getPath()});

    Main.deploy(args);
    final Properties properties = System.getProperties();
    assertEquals("/opt/test", properties.getProperty("propertyservice.propertyfolder"));
    assertEquals("true", properties.getProperty("propertyservice.distributed"));
    assertEquals("test", properties.getProperty("propertyservice.mapname"));
    Main.stop();
  }

/*  private void writePropertiesToFile(final File file) throws IOException {
    final FileOutputStream fos = new FileOutputStream(file);
    final Properties properties = new Properties();
    properties.setProperty("propertyservice.propertyfolder", "/opt/test");
    properties.setProperty("propertyservice.distributed", "true");
    properties.setProperty("propertyservice.mapname", "test");
    properties.store(fos, "temporary properties");
    fos.flush();
    fos.close();
  }*/

}
