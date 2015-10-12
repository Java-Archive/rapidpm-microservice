package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by svenruppert on 31.08.15.
 */
public class CmdLineSingleton {
  private static CmdLineSingleton ourInstance = new CmdLineSingleton();
  private List<Option> cmdLineOptions = new ArrayList<>();
  private String[] args = new String[]{};

  private CmdLineSingleton() {
  }

  public static CmdLineSingleton getInstance() {
    return ourInstance;
  }

  public Optional<CommandLine> getCommandLine() {
    try {
      final Options options = new Options();
      cmdLineOptions.forEach(options::addOption);
      CommandLine cmd = new DefaultParser().parse(options, args, true);
      return Optional.ofNullable(cmd);
    } catch (ParseException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  //  public void setCmd(final CommandLine cmd) {
//    this.cmd = cmd;
//  }
  public CmdLineSingleton addCmdLineOption(final Option option) {
    cmdLineOptions.add(option);
    return this;
  }

  public CmdLineSingleton args(final String[] args) {
    if (args == null) throw new NullPointerException("args == null is not allowed. ");
    this.args = args;
    return this;
  }

}
