package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenruppert on 11.09.15.
 */
public class DefaultCmdLineOptions implements CmdLineStartupAction {

    public static final String CMD_REST_PORT = "restPort";
    public static final String CMD_REST_HOST = "restHost";
    public static final String CMD_SERVLET_PORT = "servletPort";
    public static final String CMD_SERVLET_HOST = "servletHost";


    @Override
    public List<Option> getOptions() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(null, CMD_REST_PORT, true, "Port for REST"));
        options.add(new Option(null, CMD_REST_HOST, true, "Host IP for REST"));
        options.add(new Option(null, CMD_SERVLET_PORT, true, "Port for optionslets"));
        options.add(new Option(null, CMD_SERVLET_HOST, true, "Host IP for optionslets"));
        return options;
    }

    @Override
    public void execute(CommandLine cli) {

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
