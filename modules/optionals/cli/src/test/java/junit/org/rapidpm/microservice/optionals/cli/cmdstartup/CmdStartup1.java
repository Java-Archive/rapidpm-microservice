package junit.org.rapidpm.microservice.optionals.cli.cmdstartup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import java.util.Arrays;
import java.util.List;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class CmdStartup1 implements CmdLineStartupAction {

    public static String DESCRIPTION = "Test start 1";
    public static final String LONGOPT = "start1";
    public static final String OPT = "1";

    @Override
    public List<Option> getOptions() {

        return Arrays.asList(new Option(OPT, LONGOPT, true, DESCRIPTION));
    }

    @Override
    public void execute(CommandLine cmdLine) {

    }
}
