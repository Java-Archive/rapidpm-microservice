package junit.org.rapidpm.microservice.rest.optionals.properties.impl;

import junit.org.rapidpm.microservice.rest.optionals.properties.BaseDITest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.microservice.rest.optionals.properties.api.PropertiesStore;

import javax.inject.Inject;
import java.io.File;
import java.util.Map;
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
 * Created by RapidPM - Team on 13.12.2016.
 */
public class PropertiesStoreTest extends BaseDITest {

  static final File file = new File(PropertiesStoreTest.class.getResource("test.properties").getFile());

  @Inject
  PropertiesStore propertieStore;

  @BeforeClass
  public static void before() throws Exception {
    System.setProperty(PropertiesStore.PROPERTYFILE, file.getAbsolutePath());
  }

  @Test
  public void test001() throws Exception {
    final Optional<String> property = propertieStore.getProperty("test.001");
    assertTrue(property.isPresent());
    assertEquals("001", property.get());
  }

  @Test
  public void test002() throws Exception {
    final Optional<String> property = propertieStore.getProperty("not.there");
    assertFalse(property.isPresent());
  }

  @Test
  public void test003() throws Exception {
    final Map<String, String> namespace = propertieStore.getPropertiesOfNamespace("test");
    assertNotNull(namespace);
    assertEquals(3, namespace.size());
    assertEquals("001", namespace.get("test.001"));
  }

  @Test
  public void test004() throws Exception {
    final Map<String, String> namespace = propertieStore.getPropertiesOfNamespace("not");
    assertNotNull(namespace);
    assertTrue(namespace.isEmpty());
  }
}