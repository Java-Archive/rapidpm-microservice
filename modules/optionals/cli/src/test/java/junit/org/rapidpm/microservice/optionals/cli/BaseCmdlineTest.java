package junit.org.rapidpm.microservice.optionals.cli;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.ResponsibleFor;
import org.rapidpm.ddi.implresolver.ClassResolver;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.helper.ExitHelper;

import java.util.Optional;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class BaseCmdlineTest {


  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages(getClass().getPackage().getName());
  }


  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }


  protected int startAndGetExit(String[] args) throws Throwable {
    JunitExitHelper.reset();
    Main.deploy(Optional.of(args));
    Main.stop();
    if (JunitExitHelper.exitCalled) {
      return JunitExitHelper.exitCode;
    }
    Assert.fail("Exit not called");
    return -1;
  }

  @ResponsibleFor(ExitHelper.class)
  public static class ExitHelperResolver implements ClassResolver<ExitHelper> {

    @Override
    public Class<? extends ExitHelper> resolve(Class<ExitHelper> interf) {
      return JunitExitHelper.class;
    }
  }

  public static class JunitExitHelper implements ExitHelper {

    public static int exitCode = -1;
    public static boolean exitCalled = false;

    @Override
    public void exit(int exitCode) {
      this.exitCode = exitCode;
      exitCalled = true;
    }

    public static void reset() {
      exitCode = -1;
      exitCalled = false;
    }
  }
}
