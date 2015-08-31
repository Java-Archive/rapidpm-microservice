package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;

/**
 * Created by svenruppert on 31.08.15.
 */
public class CmdLineSingleton {
  private static CmdLineSingleton ourInstance = new CmdLineSingleton();

  public static CmdLineSingleton getInstance() {
    return ourInstance;
  }

  private CmdLineSingleton() {
  }



  private CommandLine cmd;

  public CommandLine getCmd() {
    return cmd;
  }

  public void setCmd(final CommandLine cmd) {
    this.cmd = cmd;
  }
}
