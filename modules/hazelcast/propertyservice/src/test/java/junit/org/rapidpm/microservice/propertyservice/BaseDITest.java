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
package junit.org.rapidpm.microservice.propertyservice;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.api.PropertyService;

import javax.inject.Inject;

public class BaseDITest {

  @Inject protected PropertyService propertyService;

  @BeforeEach
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages(this.getClass());
    DI.activatePackages("org.rapidpm");

    DI.activateDI(this);
    System.setProperty("mapname", this.getClass().getSimpleName());
    System.setProperty("file", this.getClass().getResource("").getPath());

    propertyService.init(this.getClass().getResource("example.properties").getPath());
    propertyService.loadProperties("example");
  }


  @AfterEach
  public void tearDown() throws Exception {

    if (propertyService != null) {
      propertyService.forget();
      propertyService.shutdown();
    }

    DI.clearReflectionModel();
  }
}
