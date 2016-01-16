package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.List;

/**
 * Created by Sven Ruppert on 18.11.2015.
 */
public interface CmdLineStartupAction {
    List<Option> getOptions();
    void execute(CommandLine cmdLine);
}
