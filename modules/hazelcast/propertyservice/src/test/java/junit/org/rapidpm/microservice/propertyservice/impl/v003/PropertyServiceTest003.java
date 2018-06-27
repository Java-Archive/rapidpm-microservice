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
package junit.org.rapidpm.microservice.propertyservice.impl.v003;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.api.PropertyService;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import java.util.Set;

public class PropertyServiceTest003 /* extends BaseDITest */ {

  private PropertyService service01;
  private PropertyService service02;

  @BeforeEach
  public void setUp() throws Exception {

    System.setProperty("propertyservice.propertyfolder", this.getClass().getResource("").getPath());
    System.setProperty("propertyservice.distributed", "true");

    DI.clearReflectionModel();
    DI.activatePackages(this.getClass());
    DI.activatePackages("org.rapidpm");

    service01 = DI.activateDI(new PropertyServiceImpl());
    service02 = DI.activateDI(new PropertyServiceImpl());

  }

  @AfterEach
  public void tearDown() throws Exception {
    service01.shutdown();
    service02.shutdown();

    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    System.setProperty("mapname", "test001");
    service01.initFromCmd();
    service01.loadProperties("example");
    service02.initFromCmd();
    final String singleProperty = service02.getSingleProperty("example.part01.001");
    Assertions.assertNotNull(singleProperty);
    //Assertions.assertFalse(singleProperty.isEmpty());
    //Assertions.assertEquals("test001", singleProperty);
  }

  @Test
  public void test002() throws Exception {
    System.setProperty("mapname", "test002");
    service01.initFromCmd();
    service02.initFromCmd();
    service02.loadProperties("example");
    final String singleProperty = service01.getSingleProperty("example.part01.001");
    Assertions.assertNotNull(singleProperty);
    //Assertions.assertFalse(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    System.setProperty("mapname", "test003");
    service01.initFromCmd();
    service02.initFromCmd();
    service01.loadProperties("example");

    final Set<String> indexToDomain = service01.getIndexOfScope("example");
    Assertions.assertNotNull(indexToDomain);
    //Assertions.assertTrue(indexToDomain.size() == 2);
  }


}
