package junit.org.rapidpm.microservice.optionals.cli;

import junit.org.rapidpm.microservice.optionals.cli.cmdstartup.CmdStartup1;
import junit.org.rapidpm.microservice.optionals.cli.cmdstartup.CmdStartup2;
import junit.org.rapidpm.microservice.optionals.cli.exeption.AttemptToExitException;
import junit.org.rapidpm.microservice.optionals.cli.exeption.NoExitSecurityManager;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupActionExecutor;

import java.util.Optional;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class MainTest005 {
    @Test
    public void test001() throws Throwable {
        System.setSecurityManager(new NoExitSecurityManager(null));
        String[] args = {"-h"};
        DI.activatePackages(CmdStartup1.class.getPackage().getName());
        int status = -1;
        try {
            Main.deploy(Optional.of(args));
        } catch (final Throwable ex) {
            final Throwable cause;

            if (ex.getCause() == null) {
                cause = ex;
            } else {
                cause = ex.getCause();
            }

            if (cause instanceof AttemptToExitException) {
                status = ((AttemptToExitException) cause).getStatus();
            } else {
                throw cause;
            }


        }
        int expectedStatus = 0;
        Assert.assertEquals("System.exit must be called with the value of " + expectedStatus, expectedStatus, status);
    }

    @Test
    public void test002() throws Exception {
        DI.clearReflectionModel();
        DI.activatePackages(CmdStartup1.class.getPackage().getName());
        CmdLineStartupActionExecutor cmdLineStartupActionExecutor = new CmdLineStartupActionExecutor();
        String[] args = {"-h"};
        cmdLineStartupActionExecutor.execute(Optional.empty());
        String helpText = CmdLineSingleton.getInstance().getHelpText();
        Assert.assertTrue(helpText.contains(CmdStartup1.DESCRIPTION));
        Assert.assertTrue(helpText.contains(CmdStartup1.LONGOPT));
        Assert.assertTrue(helpText.contains(CmdStartup1.OPT));
        Assert.assertTrue(helpText.contains(CmdStartup2.DESCRIPTION));
        Assert.assertTrue(helpText.contains(CmdStartup2.LONGOPT));
        Assert.assertTrue(helpText.contains(CmdStartup2.OPT));
    }
}
