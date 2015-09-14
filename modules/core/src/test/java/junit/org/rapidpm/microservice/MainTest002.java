package junit.org.rapidpm.microservice;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;
import org.rapidpm.microservice.optionals.cli.DefaultCmdLineOptions;

import java.time.LocalDateTime;

/**
 * Created by svenruppert on 31.08.15.
 */
public class MainTest002 {

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void test001() throws Exception {
    Main.main(new String[]{"-i /opt/hoppel/poppel/xx.properties"});

    final CommandLine cmd = CmdLineSingleton.getInstance().getCommandLine().get();
    Assert.assertNotNull(cmd);
    Assert.assertTrue(cmd.hasOption("i"));

    final String optionValue = cmd.getOptionValue("i");
    Assert.assertEquals(optionValue, " /opt/hoppel/poppel/xx.properties");

  }

  public static class DefaultCmdLineOptions implements Main.MainStartupAction {

    @Override
    public void execute() {
      System.out.println("DefaultCmdLineOptions = " + LocalDateTime.now());
      CmdLineSingleton.getInstance().addCmdLineOption(new Option("i", "inifile", true, "some usefull stuff"));
    }
  }

}
