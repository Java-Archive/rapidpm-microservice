package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.Option;
import org.rapidpm.microservice.Main;

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
  public void execute() {
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(CMD_HELP, "help", false, "some usefull stuff"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_REST_PORT, true, "Port for REST"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_REST_HOST, true, "Host IP for REST"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_SERVLET_PORT, true, "Port for Servlets"));
    CmdLineSingleton.getInstance().addCmdLineOption(new Option(null, CMD_SERVLET_HOST, true, "Host IP for Servlets"));
  }
}
