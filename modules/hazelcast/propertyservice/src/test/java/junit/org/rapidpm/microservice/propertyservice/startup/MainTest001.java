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

package junit.org.rapidpm.microservice.propertyservice.startup;

import junit.org.rapidpm.microservice.propertyservice.BaseDITest;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;
import org.rapidpm.microservice.propertyservice.impl.PropertyService;
import org.rapidpm.microservice.test.PortUtils;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Ignore
public class MainTest001 extends BaseDITest {

  @Inject
  PropertyService propertyService;

  @BeforeClass
  public static void setUpClass() {
    final PortUtils portUtils = new PortUtils();

    System.setProperty(Main.REST_HOST_PROPERTY, "127.0.0.1");
    System.setProperty(Main.SERVLET_HOST_PROPERTY, "127.0.0.1");

    System.setProperty(Main.REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(Main.SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
  }

  @Test
  public void test001() throws Exception {
    final String[] argArray = {"-fs " + this.getClass().getResource("example.properties").getPath()};
    final Optional<String[]> args = Optional.of(argArray);
    Main.deploy(args);
    Main.stop();
  }


  public static class LoadFromFSCmdLineOptions implements CmdLineStartupAction {

    public static final String OPT = "fs";

    @Override
    public List<Option> getOptions() {
      return Arrays.asList(new Option(OPT, true, "path to properties"));
    }

    @Override
    public void execute(CommandLine commandLine) {
      String optionValue = commandLine.getOptionValue(OPT);
      if (commandLine.hasOption(OPT) && !optionValue.isEmpty()) {
        System.setProperty("file", optionValue);
      }
    }

  }
}