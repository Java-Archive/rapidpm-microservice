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

import org.apache.commons.cli.Option;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;
import org.rapidpm.microservice.propertyservice.startup.PropertyFolderCmdLineOption;

import java.util.List;
import java.util.Optional;

public class PropertyFolderCmdLineOptionTest001 {

  private CmdLineStartupAction lineOptions;

  @Before
  public void setUp() throws Exception {
    lineOptions = new PropertyFolderCmdLineOption();
  }

  @Test
  public void test001() throws Exception {
    final String[] argArray = {"-propertyfolder " + this.getClass().getResource("example.properties").getPath()};
    final Optional<String[]> args = Optional.of(argArray);
    Main.deploy(args);
    Main.stop();
  }

  @Test
  public void test002() throws Exception {
    final List<Option> options = lineOptions.getOptions();
    Assert.assertNotNull(options);
    Assert.assertFalse(options.isEmpty());
    Assert.assertEquals("pf", options.get(0).getOpt());
  }
}