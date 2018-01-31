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

package junit.org.rapidpm.microservice.optionals.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;

import static org.rapidpm.microservice.optionals.cli.DefaultCmdLineOptions.CMD_REST_PORT;

public class MainTest003 extends BaseCmdlineTest {

  public static final int PORT = new PortUtils().nextFreePortForTest();


  @AfterEach
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void test001() throws Exception {


    Main.main(new String[]{"-" + CMD_REST_PORT + " " + PORT});
    String restPort = (String) System.getProperties().get(MainUndertow.REST_PORT_PROPERTY);
    Assertions.assertEquals(PORT + "", restPort);

  }


}
