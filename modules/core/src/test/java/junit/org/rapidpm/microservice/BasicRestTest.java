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

import java.util.function.Supplier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.test.RestUtils;

public class BasicRestTest {

  final RestUtils restUtils = new RestUtils();

  static Supplier<Boolean> useDocker = () -> false;

  @BeforeAll
  public static void setUpClass() {
    final PortUtils portUtils = new PortUtils();

    System.setProperty(MainUndertow.REST_HOST_PROPERTY , "127.0.0.1");
    System.setProperty(MainUndertow.SERVLET_HOST_PROPERTY , "127.0.0.1");

    if (! useDocker.get()) {
      System.setProperty(MainUndertow.REST_PORT_PROPERTY , portUtils.nextFreePortForTest() + "");
      System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY , portUtils.nextFreePortForTest() + "");
    }
  }

  @BeforeEach
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    final Class<? extends BasicRestTest> aClass = this.getClass();
    DI.activatePackages(aClass);
    Main.deploy();
  }

  @AfterEach
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }

  public String generateBasicReqURL(Class restClass) {
    final String restAppPath = MainUndertow.CONTEXT_PATH_REST;
    return restUtils.generateBasicReqURL(restClass , restAppPath);
  }

}
