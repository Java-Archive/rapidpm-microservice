package junit.org.rapidpm.microservice.optionals.cli;

import junit.org.rapidpm.microservice.optionals.cli.cmdstartup.CmdStartup1;
import junit.org.rapidpm.microservice.optionals.cli.cmdstartup.CmdStartup2;
import junit.org.rapidpm.microservice.optionals.cli.exeption.AttemptToExitException;
import junit.org.rapidpm.microservice.optionals.cli.exeption.NoExitSecurityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupActionExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class MainTest005 {

    private Method initWithArgs;
    private AtomicReference<String[]> cliRereference;

    @Before
    public void setUp() throws Exception {
        Field atomic_reference_args = CmdLineSingleton.class.getDeclaredField("ATOMIC_REFERENCE_ARGS");
        atomic_reference_args.setAccessible(true);
        cliRereference = (AtomicReference<String[]>) atomic_reference_args.get(null);

    }


    @Test
    public void test001() throws Throwable {
        String[] args = {"-h"};
        int status = startAndGetExit(args);
        int expectedStatus = 0;
        Assert.assertEquals("System.exit must be called with the value of " + expectedStatus, expectedStatus, status);
    }

    @Test
    public void test002() throws Exception {
        System.setSecurityManager(null);
        DI.clearReflectionModel();
        DI.activatePackages(CmdStartup1.class.getPackage().getName());
        CmdLineStartupActionExecutor cmdLineStartupActionExecutor = new CmdLineStartupActionExecutor();
        cliRereference.getAndSet(new String[0]);
        Main.stop();
        Main.deploy(Optional.empty());
        String helpText = CmdLineSingleton.getInstance().getHelpText();
        Assert.assertTrue(helpText.contains(CmdStartup1.DESCRIPTION));
        Assert.assertTrue(helpText.contains(CmdStartup1.LONGOPT));
        Assert.assertTrue(helpText.contains(CmdStartup1.OPT));
        Assert.assertTrue(helpText.contains(CmdStartup2.DESCRIPTION));
        Assert.assertTrue(helpText.contains(CmdStartup2.LONGOPT));
        Assert.assertTrue(helpText.contains(CmdStartup2.OPT));
        Main.stop();
    }

    @Test
    public void test003() throws Throwable {
        final String[] args = {"-trololo", "-jolo"};
        final int expectedStatus = 1;
        final int status = startAndGetExit(args);
        Assert.assertEquals("System.exit must be called with the value of " + expectedStatus, expectedStatus, status);

    }

    private int startAndGetExit(String[] args) throws Throwable {
        System.setSecurityManager(new NoExitSecurityManager(null));
        int status = -1;
        try {
            cliRereference.getAndSet(args);
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
                Main.stop();
                throw cause;
            }
        }
        return status;
    }
}
