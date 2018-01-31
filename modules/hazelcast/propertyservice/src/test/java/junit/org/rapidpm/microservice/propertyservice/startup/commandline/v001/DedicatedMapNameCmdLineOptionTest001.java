package junit.org.rapidpm.microservice.propertyservice.startup.commandline.v001;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;
import org.rapidpm.microservice.propertyservice.startup.commandline.DedicatedMapNameCmdLineOption;

import java.util.List;

public class DedicatedMapNameCmdLineOptionTest001 extends BasicRestTest {


  @Test
  public void test001() throws Exception {
    final CmdLineStartupAction lineOptions = new DedicatedMapNameCmdLineOption();
    final List<Option> options = lineOptions.getOptions();
    Assertions.assertNotNull(options);
    Assertions.assertFalse(options.isEmpty());
    Assertions.assertEquals("mn", options.get(0).getOpt());
  }


}
