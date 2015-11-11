package org.rapidpm.microservice.tools.cli.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by b.bosch on 11.11.2015.
 */
public class GetHealthCommand implements Command {
    @Override
    public boolean handlesCommand(CommandLine cmd) {
        return cmd.hasOption("get-health");
    }

    @Override
    public Optional<List<Option>> getOptions() {
        List<Option> optionList = new LinkedList<>();
        optionList.add(new Option(null, "get-health", false, "get Health information from microservice"));
        return Optional.of(optionList);
    }

    @Override
    public void doWork(CommandLine cmd) {
        System.out.println("I am a soon to be health test");
    }
}
