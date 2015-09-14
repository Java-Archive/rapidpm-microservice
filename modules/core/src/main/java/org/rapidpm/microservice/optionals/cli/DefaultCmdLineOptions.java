package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.Option;
import org.rapidpm.microservice.Main;

/**
 * Created by svenruppert on 11.09.15.
 */
public class DefaultCmdLineOptions implements Main.MainStartupAction {

  @Override
  public void execute() {
    CmdLineSingleton.getInstance().addCmdLineOption(new Option("h", "help", false, "some usefull stuff"));
  }
}
