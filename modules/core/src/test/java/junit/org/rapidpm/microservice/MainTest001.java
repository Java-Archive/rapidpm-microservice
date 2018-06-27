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
package junit.org.rapidpm.microservice;


import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.Main.MainShutdownAction;
import org.rapidpm.microservice.Main.MainStartupAction;
import org.rapidpm.microservice.MainUndertow;


public class MainTest001 {


  public static boolean status = true;

  @BeforeAll
  public static void setUpClass() {
    System.setProperty(MainUndertow.REST_PORT_PROPERTY , new PortUtils().nextFreePortForTest() + "");
    System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY , new PortUtils().nextFreePortForTest() + "");
  }

  @BeforeEach
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
  }

  @AfterEach
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    Assertions.assertTrue(status);
    Main.deploy();
    Assertions.assertFalse(status);
    Main.stop();
    Assertions.assertTrue(status);

  }

  public static class PreAction implements MainStartupAction {
    @Override
    public void execute(Optional<String[]> args) {
      status = false;
    }
  }

  public static class PostAction implements MainShutdownAction {
    @Override
    public void execute(Optional<String[]> args) {
      status = true;
    }
  }

}
