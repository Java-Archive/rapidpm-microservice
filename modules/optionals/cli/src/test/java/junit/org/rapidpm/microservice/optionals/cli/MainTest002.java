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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainTest002 extends BaseCmdlineTest {

  @Test
  public void test001() throws Exception {
    final Optional<String[]> args = Optional.of(new String[]{"-i /opt/hoppel/poppel/xx.properties"});
    Main.deploy(args);
    Assert.assertTrue(DefaultCmdLineOptions.wasproperlyExecuted);
    Main.stop();
  }

  public static class DefaultCmdLineOptions implements CmdLineStartupAction {

    public static boolean wasproperlyExecuted;

    @Override
    public List<Option> getOptions() {
      return Arrays.asList(new Option("i", "inifile", true, "some usefull stuff"));
    }

    @Override
    public void execute(CommandLine cmdLine) {
      System.out.println("DefaultCmdLineOptions = " + LocalDateTime.now());

      wasproperlyExecuted = cmdLine.hasOption("i") && "/opt/hoppel/poppel/xx.properties".equals(cmdLine.getOptionValue("i").trim());
    }
  }

}
