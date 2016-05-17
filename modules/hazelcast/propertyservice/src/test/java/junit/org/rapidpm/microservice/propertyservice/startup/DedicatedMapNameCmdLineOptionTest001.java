package junit.org.rapidpm.microservice.propertyservice.startup;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.apache.commons.cli.Option;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;
import org.rapidpm.microservice.propertyservice.startup.DedicatedMapNameCmdLineOption;

import java.util.List;
import java.util.Optional;

public class DedicatedMapNameCmdLineOptionTest001 extends BasicRestTest {


  @Before
  public void setUp() throws Exception {
    super.setUp();

  }

  @Test
  public void test001() throws Exception {
    final CmdLineStartupAction lineOptions = new DedicatedMapNameCmdLineOption();
    final List<Option> options = lineOptions.getOptions();
    Assert.assertNotNull(options);
    Assert.assertFalse(options.isEmpty());
    Assert.assertEquals("mn", options.get(0).getOpt());
  }


}
