package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by b.bosch on 18.11.2015.
 */
public class CmdLineStartupActionExecutor implements Main.MainStartupAction {
    public static final String CMD_HELP = "h";

    @Override
    public void execute(Optional<String[]> args) {
        addHelpOption();
        List<CmdLineStartupAction> startupActionInstances = getCmdLineStartupActions();
        addOptionsToCmdLineSingleton(startupActionInstances);
        executeActions(startupActionInstances);
    }

    private List<CmdLineStartupAction> getCmdLineStartupActions() {
        final Set<Class<? extends CmdLineStartupAction>> startupActions = DI.getSubTypesOf(CmdLineStartupAction.class);
        return createInstances(startupActions);
    }

    private void addHelpOption() {
        final CmdLineSingleton instance = CmdLineSingleton.getInstance();
        instance.addCmdLineOption(new Option(CMD_HELP, "help", false, "Print this page"));
    }

    private void executeActions(final List<CmdLineStartupAction> startupActionInstances) {
        final CmdLineSingleton cmdLineSingleton = CmdLineSingleton.getInstance();
        final Optional<CommandLine> commandLine = cmdLineSingleton.getCommandLine();

        if (commandLine.isPresent()) {
            final CommandLine cmdLine = commandLine.get();
            if (cmdLine.hasOption(CMD_HELP)) {
                System.out.println(cmdLineSingleton.getHelpText());
                System.exit(0);
            }
            startupActionInstances.stream().forEach(cmdLineStartupAction -> cmdLineStartupAction.execute(cmdLine));
        }
    }

    private void addOptionsToCmdLineSingleton(final List<CmdLineStartupAction> startupActionInstances) {
        final CmdLineSingleton cmdLineSingleton = CmdLineSingleton.getInstance();
        startupActionInstances.stream()
                .map(startupAction -> startupAction.getOptions())
                .flatMap(options -> options.stream())
                .forEach(opt -> cmdLineSingleton.addCmdLineOption(opt));
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
                .collect(Collectors.<T>toList());
    }
}
