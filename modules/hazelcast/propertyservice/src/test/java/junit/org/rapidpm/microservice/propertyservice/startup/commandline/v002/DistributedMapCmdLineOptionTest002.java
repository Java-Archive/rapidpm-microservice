package junit.org.rapidpm.microservice.propertyservice.startup.commandline.v002;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.propertyservice.startup.commandline.DistributedMapCmdLineOption;

import java.util.List;

public class DistributedMapCmdLineOptionTest002 extends BasicRestTest {


  @Test
  public void test001() throws Exception {
    final List<Option> options = new DistributedMapCmdLineOption().getOptions();
    Assertions.assertNotNull(options);
    Assertions.assertFalse(options.isEmpty());
    Assertions.assertEquals("dm", options.get(0).getOpt());
  }

}
