package org.rapidpm.microservice.tools.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.tools.cli.commands.Command;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by b.bosch on 11.11.2015.
 */
public class Main {

    private static final String COMMAND_PACKAGE = "org.rapidpm.microservice.tools.cli.commands";

    private Main() {
    }

    public static void main(String[] args) {
        try {
            List<Command> commands = getCommands();
            Options options = new Options();
            commands.stream()
                    .map(Command::getOptions)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .flatMap(lo -> lo.stream())
                    .forEach(o -> options.addOption(o));


            CommandLine parse = new DefaultParser().parse(options, args);

            commands.stream()
                    .filter(cmd -> cmd.handlesCommand(parse))
                    .forEach(c -> c.doWork(parse));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static List<Command> getCommands() {
        DI.activatePackages(COMMAND_PACKAGE);
        Set<Class<? extends Command>> subTypesOf = DI.getSubTypesOf(Command.class);
        return createInstances(subTypesOf);
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
