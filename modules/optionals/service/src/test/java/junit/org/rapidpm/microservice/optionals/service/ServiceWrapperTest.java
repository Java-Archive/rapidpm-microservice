package junit.org.rapidpm.microservice.optionals.service;

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

import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.service.ServiceWrapper;
import org.rapidpm.microservice.test.system.JunitExitRuntimeException;

import java.nio.file.Files;
import java.util.Arrays;

import static org.rapidpm.microservice.optionals.service.ServiceWrapper.DELAY;

public class ServiceWrapperTest {

  public static final int DELAY_WAIT_FOR_SHUTDOWN = 1000 + DELAY;

  @Test
  public void test001() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");


    try {
      ServiceWrapper.main(Arrays.asList(ServiceWrapper.SHUTDOWN).toArray(new String[1]));
    } catch (JunitExitRuntimeException e) {
      Assert.assertEquals(1, e.exitCode);
      return;
    }
    Assert.fail("exception should have been thrown");

  }

  @Test
  public void test002() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    final PortUtils portUtils = new PortUtils();
    int portForTest = portUtils.nextFreePortForTest();

    ServiceWrapper.main(Arrays.asList("-restPort=" + portForTest).toArray(new String[1]));

    // is running
    Assert.assertFalse(portUtils.isPortAvailable(portForTest));

    ServiceWrapper.main(Arrays.asList(ServiceWrapper.SHUTDOWN).toArray(new String[1]));

    // wait for shutdown
    Thread.sleep(DELAY_WAIT_FOR_SHUTDOWN);

    Assert.assertTrue(portUtils.isPortAvailable(portForTest));

  }

  @Test
  public void test003() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    String oldProperty = System.getProperty(Main.REST_PORT_PROPERTY);
    final PortUtils portUtils = new PortUtils();
    int portForTest = portUtils.nextFreePortForTest();

    System.setProperty(Main.REST_PORT_PROPERTY, String.valueOf(portForTest));
    ServiceWrapper.main(new String[0]);


    // is running
    Assert.assertFalse(portUtils.isPortAvailable(portForTest));

    ServiceWrapper.main(Arrays.asList(ServiceWrapper.SHUTDOWN).toArray(new String[1]));
    Thread.sleep(DELAY_WAIT_FOR_SHUTDOWN);

    Assert.assertTrue(portUtils.isPortAvailable(portForTest));

    System.setProperty(Main.REST_PORT_PROPERTY, oldProperty);

  }

  @Test
  public void test004() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");


    try {
      Files.createFile(ServiceWrapper.MICROSERVICE_REST_FILE);
      ServiceWrapper.main(Arrays.asList(ServiceWrapper.SHUTDOWN).toArray(new String[1]));
    } catch (JunitExitRuntimeException e) {
      Assert.assertEquals(1, e.exitCode);
      return;
    } finally {
      Files.delete(ServiceWrapper.MICROSERVICE_REST_FILE);
    }
    Assert.fail("exception should have been thrown");

  }

  @Test
  public void test005() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    final PortUtils portUtils = new PortUtils();
    int portForTest = portUtils.nextFreePortForTest();

    ServiceWrapper.main(Arrays.asList("-restPort=" + portForTest).toArray(new String[1]));
    boolean serviceExitedWithError = false;
    try {
      ServiceWrapper.main(Arrays.asList("-restPort=" + portForTest).toArray(new String[1]));
    } catch (JunitExitRuntimeException e) {
      Assert.assertEquals(1, e.exitCode);
      serviceExitedWithError = true;
    }
    // is running
    Assert.assertFalse(portUtils.isPortAvailable(portForTest));

    ServiceWrapper.main(Arrays.asList(ServiceWrapper.SHUTDOWN).toArray(new String[1]));

    // wait for shutdown
    Thread.sleep(DELAY_WAIT_FOR_SHUTDOWN);

    Assert.assertTrue(portUtils.isPortAvailable(portForTest));
    Assert.assertTrue(serviceExitedWithError);
  }

}