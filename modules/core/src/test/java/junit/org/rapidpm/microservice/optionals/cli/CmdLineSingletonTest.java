package junit.org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.*;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;
import org.rapidpm.microservice.optionals.cli.DefaultCmdLineOptions;

import java.util.Optional;

/**
 * CmdLineOptions Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 11, 2015</pre>
 */
public class CmdLineSingletonTest {

  @Test
  public void test001() throws Exception {
    final Optional<CommandLine> commandLine = CmdLineSingleton.getInstance().getCommandLine();
    Assert.assertTrue(commandLine.isPresent());
  }

  @Test
  public void test002() throws Exception {


  }




}
