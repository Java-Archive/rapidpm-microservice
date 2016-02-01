/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package junit.org.rapidpm.microservice;

import org.junit.*;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.Main.MainShutdownAction;
import org.rapidpm.microservice.Main.MainStartupAction;
import org.rapidpm.microservice.test.PortUtils;

import java.util.Optional;


public class MainTest001 {


  public static boolean status = true;

  @BeforeClass
  public static void setUpClass() {
    System.setProperty(Main.REST_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    System.setProperty(Main.SERVLET_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
  }
  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    Assert.assertTrue(status);
    Main.deploy();
    Assert.assertFalse(status);
    Main.stop();
    Assert.assertTrue(status);

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
