package junit.org.rapidpm.microservice.optionals.cli;

import junit.org.rapidpm.microservice.optionals.cli.cmdstartup.CmdStartup1;
import junit.org.rapidpm.microservice.optionals.cli.cmdstartup.CmdStartup2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.ResponsibleFor;
import org.rapidpm.ddi.implresolver.ClassResolver;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupActionExecutor;
import org.rapidpm.microservice.optionals.cli.helper.ExitHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class MainTest006 extends BaseCmdlineTest {

    @Test
    public void test001() throws Throwable {
        String[] args = {"-h"};
        int status = startAndGetExit(args);
        int expectedStatus = 0;
        Assert.assertEquals("System.exit must be called with the value of " + expectedStatus, expectedStatus, status);
    }
}
