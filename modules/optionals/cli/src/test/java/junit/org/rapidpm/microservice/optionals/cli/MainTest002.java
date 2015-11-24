package junit.org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineParser;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by svenruppert on 31.08.15.
 */
public class MainTest002 extends BaseCmdlineTest {

  @Test
  public void test001() throws Exception {
    final Optional<String[]> args = Optional.of(new String[]{"-i /opt/hoppel/poppel/xx.properties"});
    Main.deploy(args);
    Assert.assertTrue(DefaultCmdLineOptions.wasproperlyExecuted);
    Main.stop();
  }

  public static class DefaultCmdLineOptions implements CmdLineStartupAction {

    public static boolean wasproperlyExecuted = false;

    @Override
    public List<Option> getOptions() {
      return Arrays.asList(new Option("i", "inifile", true, "some usefull stuff"));
    }

    @Override
    public void execute(CommandLine cmdLine) {
      System.out.println("DefaultCmdLineOptions = " + LocalDateTime.now());

      if(cmdLine.hasOption("i") &&  "/opt/hoppel/poppel/xx.properties".equals(cmdLine.getOptionValue("i").trim())){
        wasproperlyExecuted = true;
      }
      else {
        wasproperlyExecuted = false;
      }
    }
  }

}
