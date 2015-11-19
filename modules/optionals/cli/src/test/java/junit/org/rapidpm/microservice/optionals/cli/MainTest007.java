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
public class MainTest007 {


    @Test
    public void test001() throws Exception {
        DI.clearReflectionModel();
        DI.activatePackages(CmdStartup1.class.getPackage().getName());
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


}
