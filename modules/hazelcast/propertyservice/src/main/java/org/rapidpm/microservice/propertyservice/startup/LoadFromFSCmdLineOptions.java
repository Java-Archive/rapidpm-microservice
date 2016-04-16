package org.rapidpm.microservice.propertyservice.startup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import java.util.Arrays;
import java.util.List;

public class LoadFromFSCmdLineOptions implements CmdLineStartupAction {

  public static final String OPT = "fs";

  @Override
  public List<Option> getOptions() {
    return Arrays.asList(new Option(OPT, true, "path to properties"));
  }

  @Override
  public void execute(CommandLine commandLine) {
    String optionValue = commandLine.getOptionValue(OPT);
    if (commandLine.hasOption(OPT) && !optionValue.isEmpty()) {
      System.setProperty("file", optionValue);
    }
  }

}
