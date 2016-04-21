package junit.org.rapidpm.microservice.propertyservice.startup;

import org.apache.commons.cli.Option;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;
import org.rapidpm.microservice.propertyservice.startup.DedicatedMapNameCmdLineOption;

import java.util.List;
import java.util.Optional;

public class DedicatedMapNameCmdLineOptionTest001 {

  private CmdLineStartupAction lineOptions;

  @Before
  public void setUp() throws Exception {
    lineOptions = new DedicatedMapNameCmdLineOption();
  }

  @Test
  public void test001() throws Exception {
    final String[] argArray = {"-mapname " + "test"};
    final Optional<String[]> args = Optional.of(argArray);
    Main.deploy(args);
    Main.stop();
  }

  @Test
  public void test002() throws Exception {
    final List<Option> options = lineOptions.getOptions();
    Assert.assertNotNull(options);
    Assert.assertFalse(options.isEmpty());
    Assert.assertEquals("mn", options.get(0).getOpt());
  }

}
