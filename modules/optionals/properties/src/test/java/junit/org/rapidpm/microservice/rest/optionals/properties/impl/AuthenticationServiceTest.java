package junit.org.rapidpm.microservice.rest.optionals.properties.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.rest.optionals.properties.api.AuthenticationService;

import javax.inject.Inject;
import java.io.File;

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
public class AuthenticationServiceTest extends BaseDITest {

  static final File file = new File(AuthenticationServiceTest.class.getResource("clients.txt").getFile());

  @Inject
  AuthenticationService authenticationServiceImpl;

  @BeforeAll
  public static void before() throws Exception {
    System.setProperty(AuthenticationService.CLIENTFILE, file.getAbsolutePath());
  }

  @Test
  public void test001() throws Exception {
    Assertions.assertTrue(authenticationServiceImpl.isActive());
  }

  @Test
  public void test002() throws Exception {
    Assertions.assertTrue(authenticationServiceImpl.authenticate("0.0.0.0"));
    Assertions.assertFalse(authenticationServiceImpl.authenticate("1.2.3.4"));
  }
}
