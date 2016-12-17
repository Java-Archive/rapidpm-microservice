package junit.org.rapidpm.microservice.rest.optionals.properties.startup;

import org.junit.After;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.rest.optionals.properties.api.PropertiesStore;
import org.rapidpm.microservice.rest.optionals.properties.startup.PropertyFileStartupAction;

import java.util.Optional;

import static org.junit.Assert.*;

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
public class PropertyFileStartupActionTest {

  public static final String TESTFILE_PATH = "testfile";

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void test001() throws Exception {
    Main.deploy();
    assertNull(System.getProperty(PropertiesStore.PROPERTYFILE));
  }

  @Test
  public void test002() throws Exception {
    Main.deploy(Optional.of(new String[]{"-" + PropertyFileStartupAction.COMMAND_SHORT + "=" + TESTFILE_PATH}));
    assertNotNull(System.getProperty(PropertiesStore.PROPERTYFILE));
    assertEquals(TESTFILE_PATH, System.getProperty(PropertiesStore.PROPERTYFILE));
  }

  @Test
  public void test003() throws Exception {
    Main.deploy(Optional.of(new String[]{"-" + PropertyFileStartupAction.COMMAND_LONG + "=" + TESTFILE_PATH}));
    assertNotNull(System.getProperty(PropertiesStore.PROPERTYFILE));
    assertEquals(TESTFILE_PATH, System.getProperty(PropertiesStore.PROPERTYFILE));
  }

}
