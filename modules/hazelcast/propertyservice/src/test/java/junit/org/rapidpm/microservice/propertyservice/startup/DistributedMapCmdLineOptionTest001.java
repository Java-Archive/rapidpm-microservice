package junit.org.rapidpm.microservice.propertyservice.startup;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.apache.commons.cli.Option;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;
import org.rapidpm.microservice.propertyservice.startup.DistributedMapCmdLineOption;

import java.util.List;

public class DistributedMapCmdLineOptionTest001  extends BasicRestTest {


  @Test
  public void test001() throws Exception {
    final List<Option> options = new DistributedMapCmdLineOption().getOptions();
    Assert.assertNotNull(options);
    Assert.assertFalse(options.isEmpty());
    Assert.assertEquals("dm", options.get(0).getOpt());
  }

}
