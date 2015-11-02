package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.Main;

import java.util.Optional;

/**
 * Created by svenruppert on 11.09.15.
 */
public class DefaultCmdLineOptions implements Main.MainStartupAction {

  public static final String CMD_HELP = "h";
  public static final String CMD_REST_PORT = "restPort";
  public static final String CMD_REST_HOST = "restHost";
  public static final String CMD_SERVLET_PORT = "servletPort";
  public static final String CMD_SERVLET_HOST = "servletHost";

  @Override
  public void execute(Optional<String[]> args) {
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(CMD_HELP, "help", false, "some usefull stuff"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_REST_PORT, true, "Port for REST"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_REST_HOST, true, "Host IP for REST"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_SERVLET_PORT, true, "Port for Servlets"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_SERVLET_HOST, true, "Host IP for Servlets"));

    final CmdLineSingleton cmdLineSingleton = CmdLineSingleton.getInstance();
//    if (args.isPresent()) cmdLineSingleton.args(args.get());

    final Optional<CommandLine> commandLine = cmdLineSingleton.getCommandLine();
    if (commandLine.isPresent()) {
      final CommandLine cli = commandLine.get();
      if (cli.hasOption(DefaultCmdLineOptions.CMD_SERVLET_PORT)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_SERVLET_PORT).trim();
        System.setProperty(Main.SERVLET_PORT_PROPERTY, optionValue);
      }
      if (cli.hasOption(DefaultCmdLineOptions.CMD_SERVLET_HOST)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_SERVLET_HOST).trim();
        System.setProperty(Main.SERVLET_HOST_PROPERTY, optionValue);
      }
      if (cli.hasOption(DefaultCmdLineOptions.CMD_REST_PORT)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_REST_PORT).trim();
        System.setProperty(Main.REST_PORT_PROPERTY, optionValue);
      }
      if (cli.hasOption(DefaultCmdLineOptions.CMD_REST_HOST)) {
        final String optionValue = cli.getOptionValue(DefaultCmdLineOptions.CMD_REST_HOST).trim();
        System.setProperty(Main.REST_HOST_PROPERTY, optionValue);
      }
    }
  }
}
