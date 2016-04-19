package org.rapidpm.microservice.propertyservice.startup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import java.util.Arrays;
import java.util.List;

public class DistributedMapCmdLineOption implements CmdLineStartupAction {

  public static final String OPT = "dm";

  @Override
  public List<Option> getOptions() {
    return Arrays.asList(new Option(OPT, "distributedmap", false, "enable a shared map of properties"));
  }

  @Override
  public void execute(CommandLine commandLine) {
    System.setProperty("distributed", "true");
  }

}
