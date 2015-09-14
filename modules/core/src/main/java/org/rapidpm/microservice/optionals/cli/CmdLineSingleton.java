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

  public static CmdLineSingleton getInstance() {
    return ourInstance;
  }

  private CmdLineSingleton() {
  }

  private CommandLine cmd = null;
  private String[] args = new String[]{};

  public Optional<CommandLine> getCommandLine() {
    if (cmd == null) {
      //hole alle Options aus den customer pkgs
      final Options options = new Options();
      cmdLineOptions.forEach(options::addOption);
      try {
        this.cmd = new DefaultParser().parse(options, args);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return Optional.ofNullable(cmd);
  }

//  public void setCmd(final CommandLine cmd) {
//    this.cmd = cmd;
//  }

  private List<Option> cmdLineOptions = new ArrayList<>();

  public CmdLineSingleton addCmdLineOption(final Option option) {
    cmdLineOptions.add(option);
    return this;
  }

  public CmdLineSingleton args(final String[] args) {
    this.args = args;
    return this;
  }

}
