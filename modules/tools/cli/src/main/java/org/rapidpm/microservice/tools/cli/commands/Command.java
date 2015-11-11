package org.rapidpm.microservice.tools.cli.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.List;
import java.util.Optional;

/**
 * Created by b.bosch on 11.11.2015.
 */
public interface Command {
    boolean handlesCommand(final CommandLine cmd);

    Optional<List<Option>> getOptions();

    void doWork(final CommandLine cmd);
}
