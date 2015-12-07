package junit.org.rapidpm.microservice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;

import java.util.Optional;

/**
 * Created by svenruppert on 27.08.15.
 */
public class MainTest001 {


  public static boolean status = true;


  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    Assert.assertTrue(MainTest001.status);
    Main.deploy();
    Assert.assertFalse(MainTest001.status);
    Main.stop();
    Assert.assertTrue(MainTest001.status);

  }

  public static class PreAction implements Main.MainStartupAction {
    @Override
    public void execute(Optional<String[]> args) {
      MainTest001.status = false;
    }
  }

  public static class PostAction implements Main.MainShutdownAction {
    @Override
    public void execute(Optional<String[]> args) {
      MainTest001.status = true;
    }
  }

}
