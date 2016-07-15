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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.system.ExitHandler;
import org.rapidpm.microservice.Main.MainStartupAction;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CmdLineStartupActionExecutor implements MainStartupAction {
  public static final String CMD_HELP = "h";
  private final CmdLineParser cmdLineParser = new CmdLineParser();
  @Inject
  private ExitHandler exitHelper;

  @Override
  public void execute(Optional<String[]> args) {
    addHelpOption();
    List<CmdLineStartupAction> startupActionInstances = getCmdLineStartupActions();
    addOptionsToCmdLineSingleton(startupActionInstances);
    checkCommands();
    executeActions(startupActionInstances);
  }

  private void addHelpOption() {
    cmdLineParser.addCmdLineOption(new Option(CMD_HELP, "help", false, "Print this page"));
  }

  private List<CmdLineStartupAction> getCmdLineStartupActions() {
    final Set<Class<? extends CmdLineStartupAction>> startupActions = DI.getSubTypesOf(CmdLineStartupAction.class);
    return createInstances(startupActions);
  }

  private void addOptionsToCmdLineSingleton(final List<CmdLineStartupAction> startupActionInstances) {
    startupActionInstances.stream()
        .map(CmdLineStartupAction::getOptions)
        .flatMap(Collection::stream)
        .forEach(opt -> cmdLineParser.addCmdLineOption(opt));
  }

  private void checkCommands() {
    List<String> argList = cmdLineParser.getCommandLine().get().getArgList();
    if (!argList.isEmpty()) {
      String unrecognizedCommands = argList.stream()
              .map(s -> String.format("<%s>", s))
              .reduce((s1, s2) -> s1 + ", " + s2)
              .get();
      String message = "Unrecognized commands give :" +
          unrecognizedCommands +
          "\n" +
          cmdLineParser.getHelpText();
      System.out.println(message);
      exitHelper.exit(1);
    }
  }

  private void executeActions(final List<CmdLineStartupAction> startupActionInstances) {
    final Optional<CommandLine> commandLine = cmdLineParser.getCommandLine();

    if (commandLine.isPresent()) {
      final CommandLine cmdLine = commandLine.get();
      if (cmdLine.hasOption(CMD_HELP)) {
        System.out.println(cmdLineParser.getHelpText());
        exitHelper.exit(0);
      }
      startupActionInstances.stream().forEach(cmdLineStartupAction -> cmdLineStartupAction.execute(cmdLine));
    }
  }

  private static <T> List<T> createInstances(final Set<Class<? extends T>> classes) {
    return classes
            .stream()
            .map(c -> {
              try {
                return Optional.of(c.newInstance());
              } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
              }
              return Optional.<T>empty();
            })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(DI::activateDI)
            .collect(Collectors.<T>toList());
  }
}
