package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.helper.ExitHelper;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by b.bosch on 18.11.2015.
 */
public class CmdLineStartupActionExecutor implements Main.MainStartupAction {
  @Inject
  private ExitHelper exitHelper;

  public static final String CMD_HELP = "h";

  private CmdLineParser cmdLineParser = new CmdLineParser();

  @Override
  public void execute(Optional<String[]> args) {
    addHelpOption();
    List<CmdLineStartupAction> startupActionInstances = getCmdLineStartupActions();
    addOptionsToCmdLineSingleton(startupActionInstances);
    checkCommands();
    executeActions(startupActionInstances);
  }

  private void checkCommands() {
    List<String> argList = cmdLineParser.getCommandLine().get().getArgList();
    if (!argList.isEmpty()) {
      String unrecognizedCommands = argList.stream()
              .map(s -> String.format("<%s>", s))
              .reduce((s1, s2) -> s1 + ", " + s2)
              .get();
      String message = new StringBuilder().append("Unrecognized commands give :")
              .append(unrecognizedCommands)
              .append("\n")
              .append(cmdLineParser.getHelpText()).toString();
      System.out.println(message);
      exitHelper.exit(1);
    }
  }

  private List<CmdLineStartupAction> getCmdLineStartupActions() {
    final Set<Class<? extends CmdLineStartupAction>> startupActions = DI.getSubTypesOf(CmdLineStartupAction.class);
    return createInstances(startupActions);
  }

  private void addHelpOption() {
    cmdLineParser.addCmdLineOption(new Option(CMD_HELP, "help", false, "Print this page"));
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

  private void addOptionsToCmdLineSingleton(final List<CmdLineStartupAction> startupActionInstances) {
    startupActionInstances.stream()
            .map(startupAction -> startupAction.getOptions())
            .flatMap(options -> options.stream())
            .forEach(opt -> cmdLineParser.addCmdLineOption(opt));
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
