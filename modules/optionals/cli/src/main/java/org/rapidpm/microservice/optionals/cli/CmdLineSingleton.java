package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.*;
import org.rapidpm.microservice.Main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by svenruppert on 31.08.15.
 */
public class CmdLineSingleton {
    private static CmdLineSingleton ourInstance = new CmdLineSingleton();

    private List<Option> cmdLineOptions = new ArrayList<>();

    private static final AtomicReference<String[]> ATOMIC_REFERENCE_ARGS = new AtomicReference<>(new String[]{});

    private CmdLineSingleton() {
    }

    private static void initWithArgs() {
        try {
            final Field cliArguments = Main.class.getDeclaredField("cliArguments");
            final boolean accessible = cliArguments.isAccessible();
            cliArguments.setAccessible(true);
            final Optional<String[]> cliOptional = (Optional<String[]>) cliArguments.get(null);
            if (cliOptional != null) {
                cliOptional.ifPresent(ATOMIC_REFERENCE_ARGS::set);
            }
            cliArguments.setAccessible(accessible);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static CmdLineSingleton getInstance() {
        return ourInstance;
    }

    public Optional<CommandLine> getCommandLine() {
        initWithArgs();
        try {
            final Options options = new Options();
            cmdLineOptions.forEach(options::addOption);
            CommandLine cmd = new DefaultParser().parse(options, ATOMIC_REFERENCE_ARGS.get(), true);
            return Optional.ofNullable(cmd);
        } catch (ParseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public CmdLineSingleton addCmdLineOption(final Option option) {
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
