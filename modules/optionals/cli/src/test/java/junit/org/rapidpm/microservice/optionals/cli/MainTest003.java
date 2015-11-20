package junit.org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineParser;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import static org.rapidpm.microservice.optionals.cli.DefaultCmdLineOptions.CMD_REST_PORT;

/**
 * Created by svenruppert on 14.09.15.
 */
public class MainTest003 extends BaseCmdlineTest {

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void test001() throws Exception {
    Main.main(new String[]{"-" + CMD_REST_PORT + " 1234"});
    String restPort = (String) System.getProperties().get(Main.REST_PORT_PROPERTY);
    Assert.assertEquals("1234", restPort);

  }


}
