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

package junit.org.rapidpm.microservice.optionals.header.v002;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.header.Header;
import org.rapidpm.microservice.optionals.header.HeaderInfo;

public class HeaderTest {

  @BeforeEach
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
  }

  @AfterEach
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }



  @Test
  public void test001() throws Exception {
    Main.deploy();
  }

  @Header(order = 1)
  public static class HeaderDemo01 implements HeaderInfo {
    @Override
    public String createHeaderInfo() {
      return this.getClass().getSimpleName();
    }
  }

  @Header(order = 2)
  public static class HeaderDemo02 implements HeaderInfo {
    @Override
    public String createHeaderInfo() {
      return this.getClass().getSimpleName();
    }
  }

}
