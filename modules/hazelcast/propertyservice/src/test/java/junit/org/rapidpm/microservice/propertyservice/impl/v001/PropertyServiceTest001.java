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
package junit.org.rapidpm.microservice.propertyservice.impl.v001;


import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import junit.org.rapidpm.microservice.propertyservice.BaseDITest;

@Disabled
public class PropertyServiceTest001 extends BaseDITest {

  //  @Rule
//  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private static final String PROPERTY_KEY   = "example.part01.001";
  private static final String PROPERTY_VALUE = "test001";


  @Test
  public void test001() throws Exception {
    final String singleProperty = propertyService.getSingleProperty(PROPERTY_KEY);

    Assertions.assertNotNull(singleProperty);
    Assertions.assertFalse(singleProperty.isEmpty());
    Assertions.assertEquals(PROPERTY_VALUE , singleProperty);
  }

  @Test
  public void test002() throws Exception {
    final String singleProperty = propertyService.getSingleProperty("example.invalid");
    Assertions.assertTrue(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    final Set<String> index = propertyService.getIndexOfLoadedProperties();
    System.out.println("index = " + index);
    Assertions.assertNotNull(index);
    Assertions.assertFalse(index.isEmpty());
    Assertions.assertTrue(index.contains("example.part01.001"));
    Assertions.assertTrue(index.contains("example.part01.002"));
  }

  @Test
  public void test004() throws Exception {
    final Set<String> indexToDomain = propertyService.getIndexOfScope("single");

    Assertions.assertNotNull(indexToDomain);
    Assertions.assertEquals(1 , indexToDomain.size());
    Assertions.assertTrue(indexToDomain.contains("single.theonlykey"));
  }

  @Test
  public void test005() throws Exception {
    final Map<String, String> propertiesOfDomain = propertyService.getPropertiesOfScope("single");

    Assertions.assertNotNull(propertiesOfDomain);
    Assertions.assertEquals(1 , propertiesOfDomain.size());
    Assertions.assertTrue(propertiesOfDomain.keySet().contains("single.theonlykey"));
    Assertions.assertEquals("theonlyvalue" , propertiesOfDomain.get("single.theonlykey"));
  }

}
