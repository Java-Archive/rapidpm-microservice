package junit.org.rapidpm.microservice;

import org.apache.commons.cli.CommandLine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;

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

    final CommandLine cmd = CmdLineSingleton.getInstance().getCmd();
    Assert.assertNotNull(cmd);
    Assert.assertTrue(cmd.hasOption("i"));

    final String optionValue = cmd.getOptionValue("i");
    Assert.assertEquals(optionValue, " /opt/hoppel/poppel/xx.properties");

  }
}
