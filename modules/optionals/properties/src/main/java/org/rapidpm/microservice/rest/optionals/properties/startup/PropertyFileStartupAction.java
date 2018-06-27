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
package org.rapidpm.microservice.rest.optionals.properties.startup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.optionals.cli.CmdLineStartupAction;
import org.rapidpm.microservice.rest.optionals.properties.api.PropertiesStore;

import java.util.Collections;
import java.util.List;

/**
 * Copyright (C) 2010 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by RapidPM - Team on 16.12.2016.
 */
public class PropertyFileStartupAction implements CmdLineStartupAction {

  public static final String COMMAND_SHORT = "p";
  public static final String COMMAND_LONG = "propertyfile";

  @Override
  public List<Option> getOptions() {
    return Collections.singletonList(new Option(COMMAND_SHORT, COMMAND_LONG, true, "path to propertyfile"));
  }

  @Override
  public void execute(CommandLine cmdLine) {
    if (cmdLine.hasOption(COMMAND_SHORT)) {
      final String path = cmdLine.getOptionValue(COMMAND_SHORT);
      System.setProperty(PropertiesStore.PROPERTYFILE, path);
    }
  }
}
