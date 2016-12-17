package junit.org.rapidpm.microservice.rest.optionals.properties.impl;

import junit.org.rapidpm.microservice.rest.optionals.properties.BaseDITest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.microservice.rest.optionals.properties.api.AuthenticationService;

import javax.inject.Inject;

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
public class AuthenticationServiceTest extends BaseDITest{

  private final static String path = PropertiesStoreTest.class.getResource("clients.txt").getPath().substring(1);

  @Inject
  AuthenticationService authenticationServiceImpl;

  @BeforeClass
  public static void before() throws Exception {
    System.setProperty(AuthenticationService.CLIENTFILE, path);
  }

  @Test
  public void test001() throws Exception {
    Assert.assertTrue(authenticationServiceImpl.isActive());
  }

  @Test
  public void test002() throws Exception {
    Assert.assertTrue(authenticationServiceImpl.authenticate("0.0.0.0"));
    Assert.assertFalse(authenticationServiceImpl.authenticate("1.2.3.4"));
  }
}
