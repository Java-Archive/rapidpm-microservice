package org.rapidpm.microservice.propertyservice.startup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import java.util.Arrays;
import java.util.List;

public class DedicatedMapNameCmdLineOption implements CmdLineStartupAction {

  public static final String OPT = "mn";

  @Override
  public List<Option> getOptions() {
    return Arrays.asList(new Option(OPT, "mapname", true, "set a specific name for the distributed map"));
  }

  @Override
  public void execute(CommandLine commandLine) {
    String optionValue = commandLine.getOptionValue(OPT);
    if (commandLine.hasOption(OPT) && !optionValue.isEmpty()) {
      System.setProperty("mapnam", optionValue);
    }
  }

}
