/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package junit.org.rapidpm.microservice.propertyservice.startup.commandline.v004;

import junit.org.rapidpm.microservice.BasicRestTest;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.propertyservice.startup.commandline.PropertyFolderCmdLineOption;

import java.util.List;

public class PropertyFolderCmdLineOptionTest004 extends BasicRestTest {


  @Test
  public void test001() throws Exception {
    final List<Option> options = new PropertyFolderCmdLineOption().getOptions();
    Assertions.assertNotNull(options);
    Assertions.assertFalse(options.isEmpty());
    Assertions.assertEquals("pf", options.get(0).getOpt());
  }
}