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
package junit.org.rapidpm.microservice.propertyservice.impl.v004;

import junit.org.rapidpm.microservice.propertyservice.BaseDITest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import java.lang.reflect.Method;

public class PropertyServiceTest004 extends BaseDITest {


  @Test @Disabled
  public void test001() throws Exception {
    Method method = PropertyServiceImpl.class.getDeclaredMethod("getMapName");
    method.setAccessible(true);
    final String result = (String) method.invoke(propertyService);
    Assertions.assertEquals("properties", result); // default ??
  }

  @Test
  public void test002() throws Exception {
    System.setProperty("propertyservice.mapname", PropertyServiceTest004.class.getSimpleName());

    Method method = PropertyServiceImpl.class.getDeclaredMethod("getMapName");
    method.setAccessible(true);
    final String result = (String) method.invoke(propertyService);
    Assertions.assertEquals(PropertyServiceTest004.class.getSimpleName(), result);


  }
}
