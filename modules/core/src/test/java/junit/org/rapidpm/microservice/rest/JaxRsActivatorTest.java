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
package junit.org.rapidpm.microservice.rest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.rest.JaxRsActivator;
import org.rapidpm.microservice.rest.PingMe;
import junit.org.rapidpm.microservice.rest.provider.ProviderImpl;

public class JaxRsActivatorTest {
  private JaxRsActivator jaxRsActivator = new JaxRsActivator();

  @BeforeEach
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    final Class<?> aClass = ProviderImpl.class;
    DI.activatePackages(aClass);
    DI.activatePackages(InterfaceResource.class);
  }

  @Test
  public void testGetClasses() {
    Set<Class<?>> classes = jaxRsActivator.getClasses();
    assertThat(classes.contains(PingMe.class), is(true));
    assertThat(classes.contains(ProviderImpl.class), is(true));
    assertThat(classes.contains(InterfaceResource.class), is(false));
  }

  @Test
  public void testGetPathResources() {
    Set<Class<?>> classes = jaxRsActivator.getPathResources();
    assertThat(classes.contains(PingMe.class), is(true));
    assertThat(classes.contains(ProviderImpl.class), is(false));
    assertThat(classes.contains(InterfaceResource.class), is(true));
  }

  @Test
  public void testGetInterfacesWithPathAnnotation() {
    Set<Class<?>> classes = jaxRsActivator.getInterfacesWithPathAnnotation();
    assertThat(classes.contains(PingMe.class), is(false));
    assertThat(classes.contains(ProviderImpl.class), is(false));
    assertThat(classes.contains(InterfaceResource.class), is(true));

  }

  @Path("/interfaceResource")
  public static interface InterfaceResource {
    @GET
    public String hello();
  }

  public static class InterfaceResourceImpl implements InterfaceResource {
    @Override
    public String hello() {
      return "Hello, World!";
    }
  }
}
