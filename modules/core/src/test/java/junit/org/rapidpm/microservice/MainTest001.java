package junit.org.rapidpm.microservice;

import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.Main;

/**
 * Created by svenruppert on 27.08.15.
 */
public class MainTest001 {


  @Test
  public void test001() throws Exception {
    Assert.assertTrue(MainTest001.status);
    Main.deploy();
    Assert.assertFalse(MainTest001.status);
    Main.stop();
    Assert.assertTrue(MainTest001.status);

  }

  public static boolean status = true;

  public static class PreAction implements Main.MainStartupAction{
    @Override
    public void execute() {
      MainTest001.status = false;
    }
  }
  public static class PostAction implements Main.MainShutdownAction{
    @Override
    public void execute() {
      MainTest001.status = true;
    }
  }

}
