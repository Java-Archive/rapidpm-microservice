package org.rapidpm.microservice.propertyservice.startup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import java.util.Collections;
import java.util.List;

public class PropertyFolderCmdLineOption implements CmdLineStartupAction {

  public static final String OPT = "pf";

  @Override
  public List<Option> getOptions() {
    return Collections.singletonList(new Option(OPT, "propertyfolder", true, "folder with property files"));
  }

  @Override
  public void execute(CommandLine commandLine) {
    String optionValue = commandLine.getOptionValue(OPT);
    if (commandLine.hasOption(OPT) && !optionValue.isEmpty()) {
      System.setProperty("file", optionValue);
    }
  }

}
