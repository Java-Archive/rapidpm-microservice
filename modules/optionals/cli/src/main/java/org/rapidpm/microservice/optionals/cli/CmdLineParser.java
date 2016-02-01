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

package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.*;
import org.rapidpm.microservice.Main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CmdLineParser {


  private final List<Option> cmdLineOptions = new ArrayList<>();

  private String[] args;

  public CmdLineParser() {
    initWithArgs();
  }

  private void initWithArgs() {
    try {
      final Field cliArguments = Main.class.getDeclaredField("cliArguments");
      final boolean accessible = cliArguments.isAccessible();
      cliArguments.setAccessible(true);
      final Optional<String[]> cliOptional = (Optional<String[]>) cliArguments.get(null);
      if (cliOptional != null) {
        cliOptional.ifPresent(args -> this.args = args);
      }
      cliArguments.setAccessible(accessible);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public Optional<CommandLine> getCommandLine() {
    initWithArgs();
    try {
      final Options options = new Options();
      cmdLineOptions.forEach(options::addOption);
      CommandLine cmd = new DefaultParser().parse(options, args, true);
      return Optional.ofNullable(cmd);
    } catch (ParseException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public CmdLineParser addCmdLineOption(final Option option) {
    cmdLineOptions.add(option);
    return this;
  }

  public String getHelpText() {
    return cmdLineOptions.stream()
            .map(option -> {
              String opt = option.getOpt();
              String description = option.getDescription();
              String longOpt = option.getLongOpt();
              if (opt != null && longOpt != null) {
                return String.format("-%s, -%s:  %s\n", opt, longOpt, description);
              } else if (opt != null) {
                return String.format("-%s:  %s\n", opt, description);
              } else if (longOpt != null) {
                return String.format("-%s:  %s\n", longOpt, description);
              } else {
                return "";
              }
            })
            .reduce((s1, s2) -> s1 + s2)
            .get();
  }
}
