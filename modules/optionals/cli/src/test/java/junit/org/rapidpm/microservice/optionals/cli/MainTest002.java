package junit.org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by svenruppert on 31.08.15.
 */
public class MainTest002 {

  @Before
  public void setUp() throws Exception {
    final Optional<String[]> args = Optional.of(new String[]{"-i /opt/hoppel/poppel/xx.properties"});
    Main.deploy(args);
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void test001() throws Exception {

    final CommandLine cmd = CmdLineSingleton.getInstance().getCommandLine().get();
    Assert.assertNotNull(cmd);
    Assert.assertTrue(cmd.hasOption("i"));

    final String optionValue = cmd.getOptionValue("i");
    Assert.assertEquals(optionValue.trim(), "/opt/hoppel/poppel/xx.properties");

  }

  public static class DefaultCmdLineOptions implements Main.MainStartupAction {

    @Override
    public void execute(Optional<String[]> args) {
      System.out.println("DefaultCmdLineOptions = " + LocalDateTime.now());
      CmdLineSingleton.getInstance().addCmdLineOption(new Option("i", "inifile", true, "some usefull stuff"));
    }
  }

}
